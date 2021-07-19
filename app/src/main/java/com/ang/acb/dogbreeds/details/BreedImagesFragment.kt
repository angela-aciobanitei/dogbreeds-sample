package com.ang.acb.dogbreeds.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.databinding.BreedImagesFragmentBinding
import com.ang.acb.dogbreeds.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedImagesFragment : Fragment() {

    private val viewModel: BreedImagesViewModel by viewModels()
    private val args: BreedImagesFragmentArgs by navArgs()
    private val adapter: BreedImagesAdapter by lazy { BreedImagesAdapter() }

    private var _binding: BreedImagesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BreedImagesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.getImages(args.breedName)
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvBreedImages.adapter = adapter
        binding.rvBreedImages.addItemDecoration(
            GridMarginDecoration(requireContext(), R.dimen.grid_item_spacing)
        )
    }

    private fun observeData() {
        viewModel.images.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
        })

        viewModel.message.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
    }
}
