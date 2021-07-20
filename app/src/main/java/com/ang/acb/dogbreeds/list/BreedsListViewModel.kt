package com.ang.acb.dogbreeds.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.dogbreeds.R
import com.ang.acb.dogbreeds.domain.Breed
import com.ang.acb.dogbreeds.domain.GetBreedsListUseCase
import com.ang.acb.dogbreeds.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    private val getBreedsListUseCase: GetBreedsListUseCase,
) : ViewModel() {

    private val _breeds: MutableLiveData<List<Breed>> = MutableLiveData()
    val breeds: LiveData<List<Breed>> = _breeds

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>> = _message

    private val _navigation = MutableLiveData<Event<Navigation>>()
    val navigation: LiveData<Event<Navigation>> = _navigation

    init {
        loadBreeds()
    }

    fun loadBreeds() {
        _loading.postValue(true)
        viewModelScope.launch {
            try {
                val result = getBreedsListUseCase.execute()
                if (result.isEmpty()) {
                    _message.postValue(Event(R.string.get_breeds_list_empty_message))
                }
                _breeds.postValue(result)
            } catch (e: Exception) {
                Timber.e(e)
                _message.postValue(Event(R.string.get_breeds_list_error_message))
            }
            _loading.postValue(false)
        }
    }

    fun onBreedClick(breedName: String) {
        _navigation.postValue(Event(Navigation.ToBreedImages(breedName)))
    }


    sealed class Navigation {
        data class ToBreedImages(val breedName: String) : Navigation()
    }
}
