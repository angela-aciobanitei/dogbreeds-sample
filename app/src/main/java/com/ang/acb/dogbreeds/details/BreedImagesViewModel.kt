package com.ang.acb.dogbreeds.details

import androidx.lifecycle.*
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.GetBreedImagesUseCase
import com.ang.acb.dogbreeds.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val imagesCount = 10
private const val breedNameKey = "breedName"

@HiltViewModel
class BreedImagesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
) : ViewModel() {

    private val breedName = savedStateHandle.get<String>(breedNameKey)

    private val _images: MutableLiveData<List<BreedImage>> = MutableLiveData()
    val images: LiveData<List<BreedImage>> = _images

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>> = _message

    init {
        breedName?.let { getImages(it) } ?: showError()
    }

    private fun getImages(breedName: String) {
        viewModelScope.launch {
            try {
                val result = getBreedImagesUseCase.execute(breedName, imagesCount)
                _images.postValue(result)
            } catch (e: Exception) {
                Timber.e(e)
                showError()
            }
        }
    }

    private fun showError() {
        _message.postValue(Event(R.string.get_breed_images_error_message))
    }
}
