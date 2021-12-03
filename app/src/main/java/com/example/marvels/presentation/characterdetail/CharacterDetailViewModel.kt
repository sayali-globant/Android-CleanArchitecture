package com.example.marvels.presentation.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.ApiState
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {

    private val mDetails = MutableLiveData<ApiState<MarvelCharacterResponse>>()
    val mCharacterDetails: LiveData<ApiState<MarvelCharacterResponse>>
        get() = mDetails

    internal fun getCharacterDetails(charactersRequest: CharactersRequest) {
        viewModelScope.launch {
            mDetails.postValue(ApiState.loading(null))
            getCharacterDetailsUseCase.getCharacterDetails(charactersRequest).let {
                if (it.isSuccessful) {
                    mDetails.postValue(ApiState.success(it.body()))
                } else {
                    mDetails.postValue(ApiState.error(it.errorBody().toString(), null))
                }
            }
        }
    }
}
