package com.example.marvels.presentation.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {

    private val mDetails = MutableLiveData<ApiState<CharacterModel>>()
    val mCharacterDetails: LiveData<ApiState<CharacterModel>>
        get() = mDetails

    internal fun getCharacterDetails(charactersRequest: CharactersRequestModel) {
        viewModelScope.launch {
            mDetails.postValue(ApiState.loading(null))
            getCharacterDetailsUseCase.getCharacterDetails(charactersRequest).let {
                if (it.data != null) {
                    mDetails.postValue(ApiState.success(it.data))
                } else {
                    mDetails.postValue(ApiState.error(it.message.toString(), null))
                }
            }
        }
    }
}
