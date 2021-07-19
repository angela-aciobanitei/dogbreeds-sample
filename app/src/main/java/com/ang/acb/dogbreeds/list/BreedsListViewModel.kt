package com.ang.acb.dogbreeds.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _navigation = MutableLiveData<Event<Navigation>>()
    val navigation: LiveData<Event<Navigation>> = _navigation

    init {
        viewModelScope.launch {
            try {
                val breeds = getBreedsListUseCase.execute()
                Timber.d("asd $breeds")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun onBreedClick(breedName: String) {
        _navigation.postValue(Event(Navigation.ToBreedImages(breedName)))
    }


    sealed class Navigation {
        data class ToBreedImages(val breedName: String) : Navigation()
    }
}
