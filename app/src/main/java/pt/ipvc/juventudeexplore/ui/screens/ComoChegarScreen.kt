package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.data.model.ModoTrajeto
import pt.ipvc.juventudeexplore.ui.components.BotaoAmarelo
import pt.ipvc.juventudeexplore.ui.components.Cabecalho
import pt.ipvc.juventudeexplore.ui.components.CaixaBranca
import pt.ipvc.juventudeexplore.ui.components.TituloSecao
import pt.ipvc.juventudeexplore.ui.components.corDoTransporte
import pt.ipvc.juventudeexplore.ui.components.letraTransporte
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.AzulClaro
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.Linha
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.util.abrirTrajeto
import pt.ipvc.juventudeexplore.util.minutosAPe

@Composable
fun ComoChegarScreen(pousadaId: Int, onVoltar: () -> Unit) {
    val context = LocalContext.current
    val pousada = DadosExemplo.pousada(pousadaId)
    var modo by rememberSaveable { mutableStateOf(ModoTrajeto.TRANSPORTES) }

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Cabecalho(stringResource(R.string.det_como_chegar), onVoltar)
        if (pousada == null) {
            Text(stringResource(R.string.det_nao_encontrada), modifier = Modifier.padding(20.dp))
            return@Column
        }

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OrigemDestino(pousada.nome)

            // Separadores Transportes / A pé / Bicicleta
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Branco)
                    .border(2.dp, Azul)
            ) {
                ModoTrajeto.entries.forEach { m ->
                    Surface(
                        onClick = { modo = m },
                        shape = RectangleShape,
                        color = if (m == modo) Azul else Color.Transparent,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(m.titulo).uppercase(),
                            color = if (m == modo) Color.White else Azul,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }

            if (pousada.paragens.isNotEmpty()) {
                TituloSecao(stringResource(R.string.cc_paragens), Modifier.padding(top = 8.dp))
                pousada.paragens.forEach { paragem ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Branco)
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            letraTransporte(paragem.tipo),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(corDoTransporte(paragem.tipo))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(paragem.nome, color = TextoEscuro, modifier = Modifier.weight(1f))
                        Text(
                            stringResource(
                                R.string.cc_min_a_pe,
                                minutosAPe(pousada.latitude, pousada.longitude, paragem.latitude, paragem.longitude)
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = Cinzento
                        )
                    }
                }
            }

            Text(
                stringResource(R.string.cc_carrega),
                style = MaterialTheme.typography.bodyMedium,
                color = TextoEscuro,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Branco)
                    .padding(14.dp)
            )

            Text(
                stringResource(R.string.cc_sustentavel),
                style = MaterialTheme.typography.bodySmall,
                color = TextoEscuro,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AzulClaro)
                    .padding(14.dp)
            )
        }

        BotaoAmarelo(
            stringResource(R.string.cc_abrir_maps),
            onClick = { abrirTrajeto(context, pousada.latitude, pousada.longitude, modo.googleMaps) },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun OrigemDestino(destino: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 12.dp)) {
            Box(
                Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Cinzento)
            )
            Box(
                Modifier
                    .width(2.dp)
                    .height(36.dp)
                    .background(Linha)
            )
            Box(
                Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Azul)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
            CampoLocal(stringResource(R.string.cc_tua_localizacao))
            CampoLocal(destino)
        }
    }
}

@Composable
private fun CampoLocal(texto: String) {
    CaixaBranca(Modifier.fillMaxWidth()) {
        Text(texto, color = TextoEscuro, modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp))
    }
}

@Preview(name = "Como chegar", showSystemUi = true)
@Composable
private fun ComoChegarPreview() {
    JuventudeExploreTheme(escuro = false) {
        ComoChegarScreen(pousadaId = 38, onVoltar = {})
    }
}
