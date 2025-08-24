package pl.golem.spacenews.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.golem.spacenews.navigation.Articles
import pl.golem.spacenews.navigation.Blogs
import pl.golem.spacenews.navigation.Reports
import pl.golem.spacenews.screen.articles.ArticlesScreen
import pl.golem.spacenews.screen.blogs.BlogsScreen
import pl.golem.spacenews.screen.reports.ReportsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(mainNavigation: NavController) {
    val internalNav = rememberNavController()
    val currentTab = rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            PrimaryTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = currentTab.value
            ) {
                Tab(
                    selected = currentTab.value == 0, onClick = {
                        currentTab.value = 0
                        internalNav.navigate(Articles)
                    },
                    text = {
                        Text("Articles")
                    })
                Tab(
                    selected = currentTab.value == 1, onClick = {
                        currentTab.value = 1
                        internalNav.navigate(Blogs)
                    },
                    text = {
                        Text("Blogs")
                    })
                Tab(
                    selected = currentTab.value == 2, onClick = {
                        currentTab.value = 2
                        internalNav.navigate(Reports)
                    },
                    text = {
                        Text("Reports")
                    })
            }
            NavHost(navController = internalNav, startDestination = Articles) {
                composable<Articles> {
                    ArticlesScreen(Modifier.fillMaxWidth().weight(1f), mainNavigation)
                }
                composable<Blogs> {
                    BlogsScreen(Modifier.fillMaxWidth().weight(1f), mainNavigation)
                }

                composable<Reports> {
                    ReportsScreen(Modifier.fillMaxWidth().weight(1f), mainNavigation)
                }
            }
        }
    }

    BackHandler {
        println("Back pressed")
    }
}