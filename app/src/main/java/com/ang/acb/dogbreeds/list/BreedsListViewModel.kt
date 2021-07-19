package com.ang.acb.dogbreeds.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ang.acb.dogbreeds.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedsListViewModel @Inject constructor() : ViewModel() {

    private val _navigation = MutableLiveData<Event<Navigation>>()
    val navigation: LiveData<Event<Navigation>> = _navigation

    fun onBreedClick(breedName: String) {
        _navigation.postValue(Event(Navigation.ToBreedImages(breedName)))
    }


    sealed class Navigation {
        data class ToBreedImages(val breedName: String) : Navigation()
    }
}
