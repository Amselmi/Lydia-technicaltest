package com.users.ui.detail

import com.users.domain.model.User
import com.users.ui.common.ViewAction
import com.users.ui.common.ViewEvent
import com.users.ui.common.ViewState

class UserContract {
    sealed class Event : ViewEvent {
        object GetUser : Event()
    }

    data class State(
        val user: User
    ) : ViewState

    sealed class Action : ViewAction {
        object Back : Action()
    }
}