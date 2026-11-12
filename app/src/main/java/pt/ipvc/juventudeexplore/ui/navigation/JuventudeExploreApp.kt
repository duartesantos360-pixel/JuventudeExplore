package pt.ipvc.juventudeexplore.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.ui.screens.explorar.ExplorarScreen
import pt.ipvc.juventudeexplore.ui.screens.mapa.MapaScreen
import pt.ipvc.juventudeexplore.ui.screens.perfil.ContaScreen
import pt.ipvc.juventudeexplore.ui.screens.perfil.PerfilScreen
import pt.ipvc.juventudeexplore.ui.screens.pousada.ComoChegarScreen
import pt.ipvc.juventudeexplore.ui.screens.pousada.PousadaDetalheScreen
import pt.ipvc.juventudeexplore.ui.screens.reservas.ReservaScreen
import pt.ipvc.juventudeexplore.ui.screens.reservas.ReservasScreen
import pt.ipvc.juventudeexplore.ui.screens.roteiros.RoteiroDetalheScreen
import pt.ipvc.juventudeexplore.ui.screens.roteiros.RoteirosScreen
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento

private data class ItemMenu(val rota: String, val titulo: Int, val icone: ImageVector)

/** Os 5 separadores da barra de baixo. */
private val itensMenu = listOf(
    ItemMenu(Rotas.EXPLORAR, R.string.menu_explorar, Icons.Outlined.Home),
    ItemMenu(Rotas.MAPA, R.string.menu_mapa, Icons.Outlined.Map),
    ItemMenu(Rotas.ROTEIROS, R.string.menu_roteiros, Icons.AutoMirrored.Outlined.DirectionsWalk),
    ItemMenu(Rotas.RESERVAS, R.string.menu_reservas, Icons.Outlined.DateRange),
    ItemMenu(Rotas.PERFIL, R.string.menu_perfil, Icons.Outlined.Person)
)

/**
 * Volta ao ecrã anterior. Só funciona quando o ecrã atual já acabou de abrir: assim, carregar
 * duas vezes seguidas em "voltar" não fecha dois ecrãs de uma vez.
 */
private fun NavHostController.voltar() {
    val atual = currentBackStackEntry ?: return
    if (atual.lifecycle.currentState == Lifecycle.State.RESUMED && previousBackStackEntry != null) {
        popBackStack()
    }
}

/** Navega para um separador da barra de baixo sem empilhar ecrãs repetidos. */
private fun NavHostController.irParaSeparador(rota: String) {
    navigate(rota) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun JuventudeExploreApp() {
    val navController = rememberNavController()
    val entradaAtual by navController.currentBackStackEntryAsState()
    val rotaAtual = entradaAtual?.destination?.route

    Scaffold(
        // Cada ecrã trata da barra de estado (para o cabeçalho e as fotos ficarem por baixo dela)
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            // A barra de baixo só aparece nos 5 ecrãs principais
            if (itensMenu.any { it.rota == rotaAtual }) {
                NavigationBar(containerColor = Branco) {
                    itensMenu.forEach { item ->
                        val selecionado = rotaAtual == item.rota
                        NavigationBarItem(
                            selected = selecionado,
                            onClick = { navController.irParaSeparador(item.rota) },
                            icon = { Icon(item.icone, contentDescription = null) },
                            label = {
                                Text(stringResource(item.titulo), fontWeight = if (selecionado) FontWeight.Bold else FontWeight.Normal)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Azul,
                                selectedTextColor = Azul,
                                unselectedIconColor = Cinzento,
                                unselectedTextColor = Cinzento,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Rotas.EXPLORAR,
            modifier = Modifier.padding(padding)
        ) {
            // ---------- Separadores da barra de baixo ----------
            composable(Rotas.EXPLORAR) {
                ExplorarScreen(
                    onAbrirPousada = { navController.navigate(Rotas.pousada(it)) },
                    onReservar = { navController.navigate(Rotas.reserva(it)) },
                    onVerMapa = { navController.irParaSeparador(Rotas.MAPA) }
                )
            }
            composable(Rotas.MAPA) {
                MapaScreen(
                    onAbrirPousada = { navController.navigate(Rotas.pousada(it)) },
                    onReservar = { navController.navigate(Rotas.reserva(it)) },
                    onVerLista = { navController.irParaSeparador(Rotas.EXPLORAR) }
                )
            }
            composable(Rotas.ROTEIROS) {
                RoteirosScreen(onAbrirRoteiro = { navController.navigate(Rotas.roteiro(it)) })
            }
            composable(Rotas.RESERVAS) {
                ReservasScreen(
                    onAbrirPousada = { navController.navigate(Rotas.pousada(it)) },
                    onExplorar = { navController.irParaSeparador(Rotas.EXPLORAR) }
                )
            }
            composable(Rotas.PERFIL) {
                PerfilScreen(
                    onAbrirPousada = { navController.navigate(Rotas.pousada(it)) },
                    onReservar = { navController.navigate(Rotas.reserva(it)) },
                    onEntrar = { navController.navigate(Rotas.CONTA) }
                )
            }

            // ---------- Ecrãs abertos a partir dos separadores ----------
            composable(Rotas.CONTA) {
                ContaScreen(onVoltar = { navController.voltar() })
            }
            composable(
                Rotas.POUSADA,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entrada ->
                PousadaDetalheScreen(
                    pousadaId = entrada.arguments?.getInt("id") ?: 0,
                    onVoltar = { navController.voltar() },
                    onComoChegar = { navController.navigate(Rotas.comoChegar(it)) },
                    onAbrirRoteiro = { navController.navigate(Rotas.roteiro(it)) },
                    onReservar = { navController.navigate(Rotas.reserva(it)) }
                )
            }
            composable(
                Rotas.RESERVA,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entrada ->
                ReservaScreen(
                    pousadaId = entrada.arguments?.getInt("id") ?: 0,
                    onVoltar = { navController.voltar() },
                    onVerReservas = { navController.irParaSeparador(Rotas.RESERVAS) }
                )
            }
            composable(
                Rotas.COMO_CHEGAR,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entrada ->
                ComoChegarScreen(
                    pousadaId = entrada.arguments?.getInt("id") ?: 0,
                    onVoltar = { navController.voltar() }
                )
            }
            composable(
                Rotas.ROTEIRO,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entrada ->
                RoteiroDetalheScreen(
                    roteiroId = entrada.arguments?.getInt("id") ?: 0,
                    onVoltar = { navController.voltar() }
                )
            }
        }
    }
}
