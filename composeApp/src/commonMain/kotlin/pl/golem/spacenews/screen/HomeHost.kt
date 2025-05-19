package pl.golem.spacenews.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.golem.spacenews.component.IconWithText
import pl.golem.spacenews.navigation.ArticlesScreen
import pl.golem.spacenews.navigation.BlogsScreen
import pl.golem.spacenews.navigation.ReportsScreen
import pl.golem.spacenews.screen.articles.ArticlesScreen
import pl.golem.spacenews.screen.blogs.BlogsScreen
import pl.golem.spacenews.screen.reports.ReportsScreen
import spacenews.composeapp.generated.resources.Res
import spacenews.composeapp.generated.resources.article_24dp
import spacenews.composeapp.generated.resources.articles
import spacenews.composeapp.generated.resources.blogger_icon
import spacenews.composeapp.generated.resources.blogs
import spacenews.composeapp.generated.resources.reports
import spacenews.composeapp.generated.resources.reports_icon

@Composable
fun HomeHost(mainNavController: NavController) {
    val localNavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val viewModel: HomeHostViewModel = viewModel { HomeHostViewModel() }
    val currentScreen = viewModel.currentScreen.collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold(
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "",
                    modifier = Modifier.clickable {
                        scope.launch {
                            if (scaffoldState.drawerState.isOpen)
                                scaffoldState.drawerState.close()
                            else scaffoldState.drawerState.open()
                        }
                    })

            }
        },
        scaffoldState = scaffoldState,
        drawerContent = {

        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    currentScreen.value == 0,
                    onClick = {
                        localNavController.navigate(ArticlesScreen)
                    },
                    icon = {
                        IconWithText(
                            Res.drawable.article_24dp,
                            Res.string.articles
                        )
                    }
                )
                BottomNavigationItem(
                    currentScreen.value == 1,
                    onClick = {
                        localNavController.navigate(ReportsScreen)
                    },
                    icon = {
                        IconWithText(
                            Res.drawable.reports_icon,
                            Res.string.reports
                        )
                    }
                )

                BottomNavigationItem(
                    currentScreen.value == 2,
                    onClick = {
                        localNavController.navigate(BlogsScreen)
                    },
                    icon = {
                        IconWithText(
                            Res.drawable.blogger_icon,
                            Res.string.blogs
                        )
                    }
                )
            }
        }
    ) {
        NavHost(localNavController, ArticlesScreen) {
            composable<ArticlesScreen> {
                viewModel.switchScreen(0)
                ArticlesScreen()
            }
            composable<ReportsScreen> {
                viewModel.switchScreen(1)
                ReportsScreen(localNavController)
            }
            composable<BlogsScreen> {
                viewModel.switchScreen(2)
                BlogsScreen(localNavController)
            }
        }
    }
}