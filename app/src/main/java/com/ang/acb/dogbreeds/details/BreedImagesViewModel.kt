package com.ang.acb.dogbreeds.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.dogbreeds.domain.GetBreedImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BreedImagesViewModel @Inject constructor(
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
) : ViewModel() {

    fun getImages(breedName: String) {
        viewModelScope.launch {
            try {
                val images = getBreedImagesUseCase.execute(breedName)
                Timber.d("asd images = $images")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
