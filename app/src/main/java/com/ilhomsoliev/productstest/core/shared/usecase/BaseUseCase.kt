package com.ilhomsoliev.productstest.core.shared.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}