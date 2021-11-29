package com.example.marvels.domain.usecase.base

import com.example.marvels.domain.utils.ApiError


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

