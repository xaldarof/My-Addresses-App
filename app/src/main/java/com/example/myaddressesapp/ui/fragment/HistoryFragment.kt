package com.example.myaddressesapp.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myaddressesapp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.data.cache.models.UserLocation
import com.example.myaddressesapp.databinding.FragmentHistoryBinding
import com.example.myaddressesapp.ui.adapter.AddressRecyclerAdapter
import com.example.myaddressesapp.ui.dialogs.ClearAllDialog
import com.example.myaddressesapp.ui.models.AddressUiModel
import com.example.myaddressesapp.vm.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.graphics.Color
import androidx.core.view.isVisible
import com.example.myaddressesapp.utils.animateShake
import com.example.myaddressesapp.utils.shareText
import com.example.myaddressesapp.utils.vibrate
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

@AndroidEntryPoint
class HistoryFragment : Fragment() , AddressRecyclerAdapter.CallBack,ClearAllDialog.CallBack {

    private lateinit var binding:FragmentHistoryBinding
    private val viewModel:HistoryViewModel by viewModels()
    private lateinit var adapter: AddressRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AddressRecyclerAdapter(this)
        binding.rv.adapter = adapter
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }

        binding.clearAlLBtn.setOnClickListener {
            if (adapter.itemCount > 0) {
                ClearAllDialog.Base(requireContext()).show(this)
            }else {
                binding.emptyIcon.animateShake()
                Toast.makeText(requireContext(), R.string.nothing_delete, Toast.LENGTH_SHORT).show()
                requireContext().vibrate()
            }
        }

        lifecycleScope.launch {
            viewModel.fetchAddresses().collectLatest {
                adapter.update(it)
                binding.emptyContainer.isVisible = it.isEmpty()
            }
        }

        val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean { return true }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition

                if (direction == ItemTouchHelper.RIGHT) {
                    val name = adapter.getItemNameByPosition(position)
                    viewModel.deleteAddress(name)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(requireContext(), R.string.success_delete, Toast.LENGTH_SHORT).show()
                }
                else {
                    val item = adapter.getItemByPosition(position)
                    requireContext().shareText("${item.name}\n${item.latitude}\n${item.longitude}")
                    adapter.notifyChanged()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeRightBackgroundColor(Color.RED)
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_outline_24)
                    .setSwipeRightActionIconTint(Color.WHITE)
                    .addSwipeLeftBackgroundColor(Color.GREEN)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_share_24)
                    .setSwipeLeftActionIconTint(Color.WHITE)
                    .create()
                    .decorate()

            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rv)
    }

    override fun onClickOpenMap(addressUiModel: AddressUiModel) {
        viewModel.saveUserLastLocation(UserLocation(addressUiModel.latitude,addressUiModel.longitude))
        findNavController().popBackStack()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClickClear() {
        viewModel.clearCache()
        adapter.notifyDataSetChanged()
    }
}