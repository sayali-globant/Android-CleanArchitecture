package com.example.avengers.domain.usecase.base

import com.example.avengers.domain.model.ApiError


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

