package com.example.diaryapp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.diaryapp.R
import com.example.diaryapp.data.repository.Diaries
import com.example.diaryapp.model.RequestState
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    diaries: Diaries,
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
    dateIsSelected: Boolean,
    onDateSelected: (ZonedDateTime) -> Unit,
    onDateReset: () -> Unit,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit,
) {
    var padding by remember { mutableStateOf(PaddingValues()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    NavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked,
        onDeleteAllClicked = onDeleteAllClicked
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopBar(
                    scrollBehavior = scrollBehavior,
                    onMenuClicked = onMenuClicked,
                    dateIsSelected = dateIsSelected,
                    onDateSelected = onDateSelected,
                    onDateReset =  onDateReset
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(end = padding.calculateEndPadding(LayoutDirection.Ltr)),
                    onClick = navigateToWrite
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "New Diary Icon"
                    )

                }
            },
            content = {
                padding = it
                when(diaries) {
                    is RequestState.Success -> {
                        HomeContent(
                            paddingValues = it,
                            diaryNotes = diaries.data,
                            onClick = navigateToWriteWithArgs
                        )
                    }
                    is RequestState.Error -> {
                        EmptyPage(
                            title = "Error",
                            subtitle = "${diaries.error.message}"
                        )
                    }
                    is RequestState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    else -> {}
                }
            }
        )
    }
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    content: @Composable () -> Unit
) {
   ModalNavigationDrawer(
       drawerState = drawerState,
       drawerContent = {
           ModalDrawerSheet(
               content = {
                   Box(modifier = Modifier
                       .fillMaxWidth()
                       .height(250.dp),
                       contentAlignment = Alignment.Center
                   ) {
                       Image(
                           modifier = Modifier.size(250.dp),
                           painter = painterResource(id = R.drawable.logo),
                           contentDescription = "Logo Image")
                   }
                   NavigationDrawerItem(
                       label = {
                           Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                               Icon(
                                   imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                   contentDescription = "Google Logo",
                                   tint = MaterialTheme.colorScheme.onSurface
                               )
                               Spacer(modifier = Modifier.width(12.dp))
                               Text(
                                   text = "Sign Out",
                                   color = MaterialTheme.colorScheme.onSurface
                               )
                           }
                       },
                       selected = false,
                       onClick = onSignOutClicked
                   )
                   NavigationDrawerItem(
                       label = {
                           Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                               Icon(
                                   imageVector = Icons.Default.Delete,
                                   contentDescription = "Delete All Icon",
                                   tint = MaterialTheme.colorScheme.onSurface
                               )
                               Spacer(modifier = Modifier.width(12.dp))
                               Text(
                                   text = "Delete All Diaries",
                                   color = MaterialTheme.colorScheme.onSurface
                               )
                           }
                       },
                       selected = false,
                       onClick = onDeleteAllClicked
                   )
               }
           )
       },
       content = content
   )
}