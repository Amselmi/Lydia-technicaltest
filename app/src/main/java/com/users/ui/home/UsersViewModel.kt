package com.users.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.users.common.UiState
import com.users.domain.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase:GetUsersUseCase
) : ViewModel() {

    fun getUsers(page: Int) {

        viewModelScope.launch {
        getUsersUseCase.invoke(page).onEach {
            when (it) {
                is UiState.Loading -> {
                    Log.e("Loading", "Loading")
                }

                is UiState.Success -> {

                    Log.e("Success", "" + it.data)

                }

                is UiState.Error -> {
                    Log.e("Error", "" + it.message)

                }
            }
        }.launchIn(viewModelScope)
    }
}}