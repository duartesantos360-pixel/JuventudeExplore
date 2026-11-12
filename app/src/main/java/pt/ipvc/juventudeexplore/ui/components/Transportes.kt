package pt.ipvc.juventudeexplore.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pt.ipvc.juventudeexplore.data.model.TipoTransporte
import pt.ipvc.juventudeexplore.ui.theme.AmareloEletrico
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.AzulClaro
import pt.ipvc.juventudeexplore.ui.theme.AzulMetro
import pt.ipvc.juventudeexplore.ui.theme.RoxoComboio
import pt.ipvc.juventudeexplore.ui.theme.VermelhoAutocarro

@Composable
fun corDoTransporte(tipo: TipoTransporte): Color = when (tipo) {
    TipoTransporte.A_PE -> AzulClaro
    TipoTransporte.METRO -> AzulMetro
    TipoTransporte.AUTOCARRO -> VermelhoAutocarro
    TipoTransporte.ELETRICO -> AmareloEletrico
    TipoTransporte.COMBOIO -> RoxoComboio
    TipoTransporte.BICICLETA -> Azul
}

fun letraTransporte(tipo: TipoTransporte): String = when (tipo) {
    TipoTransporte.METRO -> "M"
    TipoTransporte.AUTOCARRO -> "A"
    TipoTransporte.ELETRICO -> "E"
    TipoTransporte.COMBOIO -> "C"
    TipoTransporte.BICICLETA -> "B"
    TipoTransporte.A_PE -> "P"
}
