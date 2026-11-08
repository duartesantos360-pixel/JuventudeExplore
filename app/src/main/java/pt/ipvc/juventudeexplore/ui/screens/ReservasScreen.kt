package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro

/**
 * As minhas reservas. Por agora mostra uma reserva de exemplo, para se ver o desenho do cartão
 * (as reservas a sério ainda não são guardadas).
 */
@Composable
fun ReservasScreen(onAbrirPousada: (Int) -> Unit, onExplorar: () -> Unit) {
    val exemplo = DadosExemplo.pousada(30)

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Cabecalho(stringResource(R.string.ress_titulo))
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TituloSecao(stringResource(R.string.ress_proximas))
            if (exemplo != null) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Branco)
                        .clickable { onAbrirPousada(exemplo.id) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        EtiquetaTriangulo(stringResource(R.string.ress_reserva_codigo, "JE-2026-0001"))
                        Text(exemplo.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoEscuro)
                        Text("20/11/2026 → 22/11/2026", color = TextoEscuro)
                        Text(
                            stringResource(R.string.res_detalhe, stringResource(R.string.quarto_dormitorio)),
                            style = MaterialTheme.typography.bodySmall,
                            color = Cinzento
                        )
                        Row(Modifier.padding(top = 6.dp)) {
                            Etiqueta(stringResource(R.string.res_pagar_pousada))
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.res_total, formatarPreco(exemplo.precos.dormitorio * 2)),
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .weight(1f)
                                .background(Azul)
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                    }
                }
            }
            Text(
                stringResource(R.string.ress_mais),
                color = Cinzento,
                style = MaterialTheme.typography.bodyMedium
            )
            BotaoAmarelo(stringResource(R.string.ress_explorar), onClick = onExplorar, modifier = Modifier.fillMaxWidth())
        }
    }
}
