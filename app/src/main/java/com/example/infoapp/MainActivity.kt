package com.example.infoapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infoapp.ui.theme.InfoAppTheme
import com.example.infoapp.ui_components.DrawerMenu
import com.example.infoapp.ui_components.InfoScreen
import com.example.infoapp.ui_components.MainListItem
import com.example.infoapp.ui_components.MainScreen
import com.example.infoapp.ui_components.MainTopBar
import com.example.infoapp.utils.DrawerEvents
import com.example.infoapp.utils.IdArrayList
import com.example.infoapp.utils.ItemSaver
import com.example.infoapp.utils.ListItem
import com.example.infoapp.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var item = rememberSaveable(stateSaver = ItemSaver) {
                mutableStateOf(
                    ListItem(
                        id = 0,
                        title = "",
                        imageName = "",
                        htmlName = "",
                        isfav = false,
                        category = ""
                    )
                )
            }

            val navController = rememberNavController()
            InfoAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.MAIN_SCREEN.route
                ) {
                    composable(Routes.MAIN_SCREEN.route) {
                        MainScreen() { listItem ->
                            item.value =
                                ListItem(
                                    listItem.id,
                                    listItem.title,
                                    listItem.imageName,
                                    listItem.htmlName,
                                    listItem.isfav,
                                    listItem.category
                                )

                            navController.navigate(Routes.INFO_SCREEN.route)
                        }
                    }
                    composable(Routes.INFO_SCREEN.route) {
                        InfoScreen(item = item.value!!)
                    }
                }
            }
        }
    }
}