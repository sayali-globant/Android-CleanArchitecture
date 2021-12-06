package com.example.marvels.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(private val mGetCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val mCharactersData = MutableLiveData<ApiState<List<CharacterModel>>>()
    val mCharactersResponse: LiveData<ApiState<List<CharacterModel>>>
        get() = mCharactersData

    internal fun getCharactersList(charactersRequest: CharactersRequestModel) {
        viewModelScope.launch {
            mCharactersData.postValue(ApiState.loading(null))
            mGetCharactersUseCase.getCharacters(charactersRequest).let {
                if (it.data != null) {
                    mCharactersData.postValue(ApiState.success(it.data))
                } else {
                    mCharactersData.postValue(ApiState.error(it.message.toString(), null))
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}
