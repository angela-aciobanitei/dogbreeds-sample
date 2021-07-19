package com.ang.acb.dogbreeds.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.domain.BreedImage
import com.ang.acb.dogbreeds.domain.GetBreedImagesUseCase
import com.ang.acb.dogbreeds.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BreedImagesViewModel @Inject constructor(
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
) : ViewModel() {

    private val _images: MutableLiveData<List<BreedImage>> = MutableLiveData()
    val images: LiveData<List<BreedImage>> = _images

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>> = _message

    fun getImages(breedName: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val result = getBreedImagesUseCase.execute(breedName, 10) // todo no magic no
                Timber.d("asd images=$result")
                _images.postValue(result)
            } catch (e: Exception) {
                Timber.e(e)
                _message.postValue(Event(R.string.get_breed_images_error_message))
            }
            _loading.postValue(false)
        }
    }
}
