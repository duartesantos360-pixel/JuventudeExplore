package pt.ipvc.juventudeexplore.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

/** Preço como no site: "18,00€". */
fun formatarPreco(valor: Double): String = String.format(Locale("pt", "PT"), "%.2f€", valor)

/** "2,4 km" */
@Composable
fun formatarKm(km: Double): String =
    if (km < 10) String.format(LocalConfiguration.current.locales[0], "%.1f km", km) else "${km.toInt()} km"
