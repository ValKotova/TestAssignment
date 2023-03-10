package com.valkotova.testassignment.ui.ProductFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.valkotova.testassignment.appComponent
import com.valkotova.testassignment.databinding.FragmentProfileBinding
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import javax.inject.Inject

class ProductFragment: Fragment() {
    private var _binding: FragmentProfileBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProductViewModel by viewModels { viewModelFactory }

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}