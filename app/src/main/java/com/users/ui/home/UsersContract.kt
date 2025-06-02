package com.users.ui.home

import androidx.paging.PagingData
import com.users.data.db.UserEntity
import com.users.ui.common.ViewAction
import com.users.ui.common.ViewEvent
import com.users.ui.common.ViewState

class UsersContract {
    sealed class Event : ViewEvent {
        object GetUsers : Event()
    }

    data class State(
        val users: PagingData<UserEntity>
    ) : ViewState

    sealed class Action : ViewAction {
        data class ToDetail(val userId: String) : Action()
    }
}
