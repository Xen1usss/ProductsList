package ru.xen.productslist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.xen.productslist.databinding.FragmentProductsBinding
import ru.xen.productslist.utils.DimensionUtils
import ru.xen.productslist.utils.EndlessRecyclerViewScrollListener

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding
        get() = _binding!!
    private val viewModel: ProductsViewModel by inject()
    private val adapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadProducts()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products
                .filterNotNull()
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { products ->
                    adapter.setData(products)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { loadingState ->
                    when (loadingState) {
                        ProductsViewModel.Loading.TOP -> {
                            enableScrollListener(false)
                            binding.swipeRefreshLayout.isRefreshing = true
                            binding.swipeRefreshLayout.setProgressViewEndTarget(
                                false,
                                DimensionUtils.getActionBarHeight(requireActivity())
                            )
                        }
                        ProductsViewModel.Loading.BOTTOM -> {
                            enableScrollListener(false)
                            binding.swipeRefreshLayout.isRefreshing = true
                            binding.swipeRefreshLayout.setProgressViewEndTarget(
                                false,
                                DimensionUtils.getScreenHeight() - DimensionUtils.getActionBarHeight(
                                    requireActivity()
                                ) * 3
                            )
                        }
                        ProductsViewModel.Loading.NONE -> {
                            enableScrollListener(true)
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { errorState ->
                    if (errorState != null) {
                        Snackbar.make(requireView(), errorState, Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun enableScrollListener(enableScrollListener: Boolean) {
        binding.rcView.clearOnScrollListeners()
        if (enableScrollListener) {
            binding.rcView.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {

                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    if (totalItemsCount % ProductsViewModel.LIMIT != 0) {
                        enableScrollListener(false)
                    } else {
                        enableScrollListener(false)
                        viewModel.loadProducts(totalItemsCount)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}