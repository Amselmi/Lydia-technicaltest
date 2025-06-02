package com.users.domain.usesCase

import androidx.paging.PagingData
import com.users.domain.repository.Repository
import com.users.domain.common.UseCase
import com.users.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<Unit, Flow<PagingData<User>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<User>> {
        return repository.getUsers()
    }
}

