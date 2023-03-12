package com.valkotova.testassignment.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valkotova.presenter.R
import com.valkotova.testassignment.appComponent
import com.valkotova.testassignment.databinding.FragmentProductBinding
import com.valkotova.model.Product
import com.valkotova.presenter.di.loadGlide
import com.valkotova.presenter.ext.showError
import com.valkotova.testassignment.databinding.HolderImagesBinding
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import javax.inject.Inject

class ProductFragment: Fragment() {
    private var _binding: FragmentProductBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProductViewModel by viewModels { viewModelFactory }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val product = arguments?.getParcelable<Product>("product")
//        product?.let{
//            viewModel.setProduct(product)
//        }

        with(binding) {
            viewModel.state.observe(viewLifecycleOwner) { state ->
                when (state) {
                    ProductStates.Empty -> {
                    }
                    ProductStates.NavigateBack -> {
                        findNavController().navigateUp()
                    }
                    is ProductStates.ShowError -> {
                        binding.root.showError(state.error)
                    }
                }
                viewModel.emptyState()
            }

            viewModel.product.observe(viewLifecycleOwner) { product ->
                product?.let{
                    tvName.text = product.name
                    tvDescription.text = product.description
                    tvRating.text = String.format("%.1f", product.rating)
                    tvReview.text = getString(R.string.reviews_amount, product.numberOfReviews)
                    tvPrice.text = getString(R.string.price_value, product.price)
                }
            }

            viewModel.selectedImage.observe(viewLifecycleOwner){
                it?.let{
                    ivMainImage.loadGlide(it)
                }
            }

            val colorsAdapter = ColorsList()
            rvColors.adapter = colorsAdapter
            rvColors.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            colorsAdapter.setItemOnClickListener{ position, item ->
                viewModel.onColorItemClick(position)
            }
            viewModel.colorsList.observe(viewLifecycleOwner) {
                colorsAdapter.submitList(it)
                colorsAdapter.notifyDataSetChanged()
            }

            val imagesAdapter = object : com.valkotova.presenter.views.lists.CircularAdapter<ImageItem, ImageViewHolder>(){
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ImageViewHolder {
                    return ImageViewHolder(
                        HolderImagesBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ))
                }
            }
            rvImages.adapter = imagesAdapter
            rvImages.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            imagesAdapter.setItemOnClickListener{ position, item ->
                viewModel.onImageItemClick(position)
            }
            viewModel.imagesList.observe(viewLifecycleOwner) {
                if(it.isNotEmpty()) {
                    if (imagesAdapter.itemCount == 0)
                        imagesAdapter.submitList(it) {
                            rvImages.scrollToPosition((Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % it.size)
                        }
                    else {
                        imagesAdapter.submitList(it)
                        imagesAdapter.notifyDataSetChanged()
                    }
                } else
                    imagesAdapter.submitList(it)
            }

            viewModel.total.observe(viewLifecycleOwner){
                //tvTotal.text = getString(R.string.price_value, it)
                tvTotal.text = String.format("%.0f", it)
            }

            btnAdd.setOnClickListener {
                viewModel.addTotal(1)
            }
            btnSubstract.setOnClickListener{
                viewModel.addTotal(-1)
            }
            btnBack.setOnClickListener{
                viewModel.backOnClick()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}