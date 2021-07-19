package com.ang.acb.dogbreeds.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ang.acb.dogbreeds.databinding.BreedImagesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BreedImagesFragment : Fragment() {

    private val viewModel: BreedImagesViewModel by viewModels()
    private val args: BreedImagesFragmentArgs by navArgs()

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

        Timber.d("asd args = $args")
        viewModel.getImages(args.breedName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
