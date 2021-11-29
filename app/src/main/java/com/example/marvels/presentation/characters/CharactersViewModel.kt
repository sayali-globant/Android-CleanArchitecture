package com.example.marvels.presentation.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvels.data.entity.AvengerCharacterResponse
import com.example.marvels.data.entity.request.CharactersRequest
import com.example.marvels.domain.usecase.AvengerUseCase
import com.example.marvels.domain.usecase.base.UseCaseResponse
import com.example.marvels.domain.utils.ApiError
import kotlinx.coroutines.cancel


class CharactersViewModel constructor(private val getPostsUseCase: AvengerUseCase) : ViewModel() {

    val mCharactersData = MutableLiveData<AvengerCharacterResponse>()
    val mShowProgressbar = MutableLiveData<Boolean>()
    val mErrorData = MutableLiveData<String>()

    fun getCharactersList(charactersRequest: CharactersRequest) {
        mShowProgressbar.value = true
        getPostsUseCase.invoke(
            viewModelScope, charactersRequest,
            object :
                UseCaseResponse<AvengerCharacterResponse> {
                override fun onSuccess(result: AvengerCharacterResponse) {
                    Log.i(TAG, "result: $result")
                    mCharactersData.value = result
                    mShowProgressbar.value = false
                }

                override fun onError(apiError: ApiError?) {
                    mErrorData.value = apiError?.getErrorMessage()
                    mShowProgressbar.value = false
                }
            },
        )
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = CharactersViewModel::class.java.name
    }

}
