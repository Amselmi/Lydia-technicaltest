package com.users.domain.usesCase

import androidx.paging.PagingData
import com.users.data.db.UserEntity
import com.users.data.repository.Repository
import com.users.domain.common.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<Unit, Flow<PagingData<UserEntity>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<UserEntity>> {
        return repository.getUsers()
    }
}

