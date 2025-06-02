package com.users.ui.detail

import androidx.lifecycle.viewModelScope
import com.users.domain.usesCase.GetUserByIdUseCase
import com.users.ui.common.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailUserViewModel.DetailUserViewModelFactory::class)
class DetailUserViewModel @AssistedInject constructor(
    @Assisted val userId: String,
    private val getUserUseCase: GetUserByIdUseCase
) : BaseViewModel<UserContract.Event, UserContract.State, UserContract.Action>() {

    @AssistedFactory
    interface DetailUserViewModelFactory {
        fun create(userId: String): DetailUserViewModel
    }

    init {
        setEvent(UserContract.Event.GetUser)
    }

    private fun getUserById(userId: String) {
        viewModelScope.launch(Default) {
            getUserUseCase.execute(userId).also {
                setState { copy(user = it) }
            }
        }
    }

    override fun setInitialState() = UserContract.State(user = null)

    override fun handleEvents(event: UserContract.Event) {
        when (event) {
            UserContract.Event.GetUser -> getUserById(userId)
        }
    }


}