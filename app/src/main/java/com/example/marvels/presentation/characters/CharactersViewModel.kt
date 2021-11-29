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

    val postsData = MutableLiveData<AvengerCharacterResponse>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()

    fun getCharactersList(charactersRequest: CharactersRequest) {
        showProgressbar.value = true
        getPostsUseCase.invoke(viewModelScope, charactersRequest, object :
            UseCaseResponse<AvengerCharacterResponse> {
                override fun onSuccess(result: AvengerCharacterResponse) {
                    Log.i(TAG, "result: $result")
                    postsData.value = result
                    showProgressbar.value = false
                }

                override fun onError(apiError: ApiError?) {
                    messageData.value = apiError?.getErrorMessage()
                    showProgressbar.value = false
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