package com.users.domain.usesCase

import com.users.data.db.UserEntity
import com.users.data.repository.Repository
import com.users.domain.common.UseCase
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<String,UserEntity?> {
    override suspend fun execute(input: String): UserEntity?{
        return repository.getUserByUserId(input)
    }

}