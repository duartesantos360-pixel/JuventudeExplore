package pt.ipvc.juventudeexplore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.ipvc.juventudeexplore.ui.navigation.JuventudeExploreApp
import pt.ipvc.juventudeexplore.ui.screens.AberturaScreen
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Arranque do sistema (verde, enquanto a app carrega); tem de vir antes do super.onCreate
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuventudeExploreTheme {
                // A abertura só aparece quando a app arranca (não ao rodar o ecrã)
                var abertura by rememberSaveable { mutableStateOf(savedInstanceState == null) }
                if (abertura) AberturaScreen(onFim = { abertura = false }) else JuventudeExploreApp()
            }
        }
    }
}
