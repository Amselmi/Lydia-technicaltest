package com.users.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.lydiatest.R
import com.users.ui.home.composables.ErrorMessage
import com.users.ui.home.composables.ItemUser
import com.users.ui.home.composables.Loader
import com.users.ui.home.composables.LoadingNextPageItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun UsersScreen(
    state: State<UsersContract.State>,
    onNavigationRequested: (action: UsersContract.Action) -> Unit
) {

    val usersItems = flowOf(state.value.users).collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .weight(1.0f),
                    textAlign = TextAlign.Center
                )


            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            state = listState
        ) {
            item { Spacer(modifier = Modifier.padding(4.dp)) }
            items(usersItems.itemCount) { index ->
                usersItems[index]?.let { user ->
                    ItemUser(
                        user = user,
                        onClick = {
                            onNavigationRequested(UsersContract.Action.ToDetail(user.userId))
                        }
                    )
                }
            }
            usersItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { Loader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }
    }
}