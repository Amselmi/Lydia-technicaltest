package com.users.domain.common


interface UseCase<In, Out>{
    suspend fun execute(input: In): Out
}