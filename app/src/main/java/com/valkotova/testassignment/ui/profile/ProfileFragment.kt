package com.valkotova.testassignment.ui.profile

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.valkotova.testassignment.R
import com.valkotova.testassignment.appComponent
import com.valkotova.testassignment.databinding.FragmentProfileBinding
import com.valkotova.testassignment.databinding.FragmentSignInBinding
import com.valkotova.testassignment.di.loadGlide
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import com.valkotova.testassignment.ui.ext.showError
import com.valkotova.testassignment.ui.login.LogInStates
import com.valkotova.testassignment.ui.signIn.SignInViewModel
import java.io.File
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { viewModelFactory }

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) return@registerForActivityResult
        viewModel.setImageUrl(uri.toString())
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if(result[Manifest.permission.READ_EXTERNAL_STORAGE] != null
                || result[Manifest.permission.READ_MEDIA_IMAGES] != null){
                if (result.all { it.value }) {
                    pickImage.launch("image/*")
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is ProfileStates.ShowError -> {
                    binding.root.showError(state.error)
                }
                ProfileStates.Empty -> {
                }
                ProfileStates.NavigateToSignIn -> {
                    findNavController().navigate(R.id.navigation_sign_in)
                }
                ProfileStates.ChangeAvatar -> {
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                        requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                    else
                        requestPermission.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
                }
            }
            viewModel.emptyState()
        }

        viewModel.imageUrl.observe(viewLifecycleOwner){ imageUrl ->
            imageUrl?.let{
                binding.ivAvatar.loadGlide(it)
            } ?: run {
                binding.ivAvatar.setImageResource(R.drawable.photo_sample)
            }
        }

        binding.menuLogOut.setOnClickListener{
            viewModel.logOut()
        }

        binding.avatarContainer.setOnClickListener {
            viewModel.onChangeAvatar()
        }
        return binding.root
    }
}