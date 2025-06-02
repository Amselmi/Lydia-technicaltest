package com.users.domain.usesCase

import com.users.domain.repository.Repository
import com.users.domain.common.UseCase
import com.users.domain.model.User
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<String, User?> {
    override suspend fun execute(input: String): User{
        return repository.getUserByUserId(input)
    }

}