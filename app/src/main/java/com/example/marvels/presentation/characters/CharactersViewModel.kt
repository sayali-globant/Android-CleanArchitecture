package com.example.marvels.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvels.domain.utils.NetworkHelper
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.mydomain.ApiState
import com.marvel.mydomain.usecase.characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val mGetCharactersUseCase: GetCharactersUseCase,
    private val mNetworkHelper: NetworkHelper
) : ViewModel() {

    private val mCharactersData = MutableLiveData<ApiState<MarvelCharacterResponse>>()
    val mCharactersResponse: LiveData<ApiState<MarvelCharacterResponse>>
        get() = mCharactersData

    internal fun getCharactersList(charactersRequest: CharactersRequest) {
        viewModelScope.launch {
            mCharactersData.postValue(ApiState.loading(null))
            if (mNetworkHelper.isNetworkAvailable()) {
                mGetCharactersUseCase.getCharacters(charactersRequest).let {
                    if (it.isSuccessful) {
                        mCharactersData.postValue(ApiState.success(it.body()))
                    } else {
                        mCharactersData.postValue(ApiState.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                mCharactersData.postValue(ApiState.error("No internet connection", null))
            }
        }

    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = CharactersViewModel::class.java.name
    }

}
