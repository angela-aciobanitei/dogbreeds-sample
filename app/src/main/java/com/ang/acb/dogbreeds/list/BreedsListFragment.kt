package com.ang.acb.dogbreeds.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.databinding.BreedsListFragmentBinding
import com.ang.acb.dogbreeds.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsListFragment : Fragment() {

    private val viewModel: BreedsListViewModel by viewModels()

    // See: https://developer.android.com/topic/libraries/view-binding#fragments
    private var _binding: BreedsListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BreedsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helloBreeds.setOnClickListener {
            viewModel.onBreedClick("hound")
        }

        observeNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeNavigation() {
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver { navigation ->
            val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            when (navigation) {
                is BreedsListViewModel.Navigation.ToBreedImages -> {
                    navController.navigate(
                        BreedsListFragmentDirections.actionBreedsListToBreedImages(navigation.breedName)
                    )
                }
            }
        })
    }
}
