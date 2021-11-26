package com.example.avengers.presentation.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avengers.domain.model.ApiError
import com.example.avengers.domain.model.AvengerCharacterResponse
import com.example.avengers.domain.model.request.CharactersRequest
import com.example.avengers.domain.usecase.AvengerUseCase
import com.example.avengers.domain.usecase.base.UseCaseResponse
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