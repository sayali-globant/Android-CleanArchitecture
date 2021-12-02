package com.example.marvels.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.ApiState
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(private val mGetCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val mCharactersData = MutableLiveData<ApiState<MarvelCharacterResponse>>()
    val mCharactersResponse: LiveData<ApiState<MarvelCharacterResponse>>
        get() = mCharactersData

    internal fun getCharactersList(charactersRequest: CharactersRequest) {
        viewModelScope.launch {
            mCharactersData.postValue(ApiState.loading(null))
            mGetCharactersUseCase.getCharacters(charactersRequest).let {
                if (it.isSuccessful) {
                        mCharactersData.postValue(ApiState.success(it.body()))
                    } else {
                        mCharactersData.postValue(ApiState.error(it.errorBody().toString(), null))
                    }
                }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}
