package com.valkotova.testassignment.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.valkotova.testassignment.R
import com.valkotova.testassignment.appComponent
import com.valkotova.testassignment.databinding.FragmentHomeBinding
import com.valkotova.testassignment.databinding.HolderCategoryBinding
import com.valkotova.testassignment.databinding.HolderFlashSaleBinding
import com.valkotova.testassignment.databinding.HolderLatestBinding
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import com.valkotova.testassignment.ui.ext.textChanges
import com.valkotova.testassignment.ui.views.lists.BaseViewHolder
import com.valkotova.testassignment.ui.views.lists.CircularAdapter
import com.valkotova.testassignment.ui.home.holders.CategoryViewHolder
import com.valkotova.testassignment.ui.home.holders.FlashSaleViewHolder
import com.valkotova.testassignment.ui.home.holders.LatestViewHolder
import com.valkotova.testassignment.ui.home.items.CategoryItem
import com.valkotova.testassignment.ui.home.items.FlashSaleItem
import com.valkotova.testassignment.ui.home.items.LatestItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapterLatest = object : CircularAdapter<LatestItem, LatestViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder<LatestItem> {
                return LatestViewHolder(HolderLatestBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
        }
    }
    private val adapterFlashSale = object : CircularAdapter <FlashSaleItem, FlashSaleViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder<FlashSaleItem> {
            return FlashSaleViewHolder(HolderFlashSaleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }
    }

    private val adapterCategory = object : CircularAdapter <CategoryItem, CategoryViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CategoryViewHolder {
            return CategoryViewHolder(
                HolderCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        with(binding){
            rvLatest.adapter = adapterLatest
            rvLatest.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            viewModel.latest.observe(viewLifecycleOwner) {
                adapterLatest.submitList(it) {
                    if (it.isNotEmpty())
                        rvLatest.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % it.size)
                }
            }

            rvFlashSale.adapter = adapterFlashSale
            rvFlashSale.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            viewModel.flashSale.observe(viewLifecycleOwner) {
                adapterFlashSale.submitList(it) {
                    if (it.isNotEmpty())
                        rvFlashSale.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % it.size)
                }
            }
            rvCategories.adapter = adapterCategory
            rvCategories.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            viewModel.initCategory().let{
                adapterCategory.submitList(it) {
                    if (it.isNotEmpty())
                        rvCategories.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % it.size)
                }
            }

            searchContainer
                .textChanges()
                .debounce(1000L)
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .onEach {
                    viewModel.setSearchText(it.toString())
                }.launchIn(lifecycleScope)

            searchContainer.doOnTextChanged({_, _, _ ,_ ->
                viewModel.notShowSuggestion()
            })

            val popup = ListPopupWindow(requireContext())
            popup.anchorView = searchContainer
            popup.setAdapter(ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                listOf()
            ))
            viewModel.suggestions.observe(viewLifecycleOwner){
                Log.d("Suggestions", it.toString())
                if(it.isNotEmpty()) {
                    popup.setAdapter(ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        it
                    ))
                    popup.setOnItemClickListener(object : AdapterView.OnItemClickListener{
                        override fun onItemClick(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            searchContainer.setText(viewModel.suggestions.value?.get(position) ?:"")
                            searchContainer.setSelection(searchContainer.text.length)
                            viewModel.setSearchText(it[position], true)
                        }

                    })
                    popup.show()
                } else {
                    popup.dismiss()
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}