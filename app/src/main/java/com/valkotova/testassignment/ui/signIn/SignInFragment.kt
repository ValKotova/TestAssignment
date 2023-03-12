package com.valkotova.testassignment.ui.signIn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.valkotova.presenter.ext.showError
import com.valkotova.presenter.ext.updateState
import com.valkotova.testassignment.appComponent
import com.valkotova.testassignment.databinding.FragmentSignInBinding
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import javax.inject.Inject

class SignInFragment: Fragment() {
    private var _binding: FragmentSignInBinding? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SignInViewModel by viewModels { viewModelFactory }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.etFirstName.setText(viewModel.firstName.value)
        binding.etLastName.setText(viewModel.lastName.value)
        binding.etEmail.setText(viewModel.email.value)

        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is SignInState.ShowError -> {
                    binding.root.showError(state.error)
                }
                SignInState.Empty -> {
                }
                SignInState.NavigateToHome -> {
                    findNavController().navigate(SignInFragmentDirections.actionSigninToHome())
                }
                SignInState.NavigateToLogIn -> {
                    findNavController().navigate(SignInFragmentDirections.actionSigninToLogin())
                }
            }
            viewModel.emptyState()
        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etFirstName.doOnTextChanged { text, _, _, _ ->
            viewModel.setFirstName(text.toString())
        }
        binding.etLastName.doOnTextChanged { text, _, _, _ ->
            viewModel.setLastName(text.toString())
        }
        binding.btnSignIn.setOnClickListener {
            viewModel.onSignIn()
        }
        binding.tvLogIn.setOnClickListener {
            viewModel.onLogIn()
        }
        viewModel.emailIsInvalid.observe(viewLifecycleOwner) {
            binding.etEmail.updateState(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}