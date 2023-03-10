package com.valkotova.testassignment.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.valkotova.testassignment.R
import com.valkotova.testassignment.appComponent
import com.valkotova.testassignment.databinding.FragmentLogInBinding
import com.valkotova.testassignment.databinding.FragmentProfileBinding
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import com.valkotova.testassignment.ui.ext.showError
import com.valkotova.testassignment.ui.ext.updateState
import com.valkotova.testassignment.ui.profile.ProfileViewModel
import com.valkotova.testassignment.ui.signIn.SignInState
import javax.inject.Inject

class LogInFragment: Fragment() {
    private var _binding: FragmentLogInBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: LogInViewModel by viewModels { viewModelFactory }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is LogInStates.ShowError -> {
                    binding.root.showError(state.error)
                }
                LogInStates.Empty -> {
                }
                LogInStates.NavigateToHome -> {
                    findNavController().navigate(R.id.navigation_home)
                }
            }
            viewModel.emptyState()
        }

        viewModel.firstNameIsInvalid.observe(viewLifecycleOwner){
            binding.etFirstName.updateState(it)
        }

        viewModel.isPasswordVisible.observe(viewLifecycleOwner){
            if (it) {
                binding.togglePassword.setImageResource(R.drawable.ic_eye_on)
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.togglePassword.setImageResource(R.drawable.ic_eye_off)
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            if (binding.etPassword.isFocused) {
                binding.etPassword.setSelection(binding.etPassword.length())
            }
        }

        binding.btnLogIn.setOnClickListener {
            viewModel.onLogIn()
        }

        binding.etFirstName.doAfterTextChanged {
            viewModel.setFirstName(it?.toString()?:"")
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.setPassword(it?.toString()?:"")
        }

        binding.togglePassword.setOnClickListener {
            viewModel.changePasswordVisibility()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}