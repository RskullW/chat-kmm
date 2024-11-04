package com.chatkmm.root

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chatkmm.base.features.enum.Screen
import com.chatkmm.data.utils.Log
import com.chatkmm.data.utils.globalApplicationContext
import com.chatkmm.entity.enums.ErrorType
import com.chatkmm.root.di.startKoin
import com.chatkmm.root.presentation.RootViewModel
import com.chatkmm.screen.authorization.AuthorizationScreen
import com.chatkmm.screen.menu.MenuScreen
import com.chatkmm.screen.registration.RegistrationScreen
import com.chatkmm.screen.splash.SplashScreen
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class RootScreenApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RootScreenApplication)
            androidLogger(level = Level.DEBUG)
        }

    }
}

class RootActivity : ComponentActivity() {
    private lateinit var viewModel: RootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getKoin().get()

        setContent {
            globalApplicationContext = LocalContext.current
            RootApp(viewModel)
        }
    }
}

@Composable
fun RootApp(viewModel: RootViewModel) {
    val navController = rememberNavController()

    val screen by viewModel.screen.state.collectAsState()

    LaunchedEffect(screen) {
        val currentDestination = navController.currentBackStackEntry?.destination?.route
        if (currentDestination != Screen.SPLASH.toString() && screen == null) {
            navController.popBackStack()
            return@LaunchedEffect
        }
        val destination = screen?.toString()

        if (destination == null) {
            Log("RootApp", "Screen $screen not opened", errorType = ErrorType.ERROR)
            return@LaunchedEffect
        }

        if (currentDestination != destination) {
            if (viewModel.isClearStack) {
                navController.navigate(destination) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(destination)
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.SPLASH.toString()) {
        composable(Screen.SPLASH.toString()) {
            SplashScreen()
            BackHandler(true) {}
        }
        composable(Screen.AUTHORIZATION.toString()) {
            AuthorizationScreen()
            BackHandler(true) {}
        }
        composable(Screen.REGISTRATION.toString()) {
            RegistrationScreen()
            BackHandler(true) {}
        }
        composable(Screen.MENU.toString()) {
            MenuScreen()
            BackHandler(true) {}
        }
    }
}