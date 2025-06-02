package com.users.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData.Companion.empty
import androidx.paging.cachedIn
import com.users.domain.usesCase.GetUsersUseCase
import com.users.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<UsersContract.Event, UsersContract.State, UsersContract.Action>() {
    init {
        setEvent(UsersContract.Event.GetUsers)
    }

    private fun getUsers() {
        viewModelScope.launch(Default) {
            getUsersUseCase.execute(Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { result ->
                    setState { copy(users = result) }
                }
        }
    }

    override fun setInitialState() = UsersContract.State(
        empty()
    )

    override fun handleEvents(event: UsersContract.Event) {
        when (event) {
            UsersContract.Event.GetUsers -> getUsers()
        }
    }
}