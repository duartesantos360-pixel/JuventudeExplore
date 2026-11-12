package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.osmdroid.util.GeoPoint
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.Laranja
import pt.ipvc.juventudeexplore.ui.theme.Linha
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.Unbounded

@Composable
fun RoteiroDetalheScreen(roteiroId: Int, onVoltar: () -> Unit) {
    val context = LocalContext.current
    val roteiro = DadosExemplo.roteiro(roteiroId)
    val pousada = roteiro?.let { DadosExemplo.pousada(it.pousadaId) }

    if (roteiro == null || pousada == null) {
        Column(Modifier.fillMaxSize().statusBarsPadding()) {
            Cabecalho(stringResource(R.string.rotd_roteiro), onVoltar)
            Text(stringResource(R.string.rotd_nao_encontrado), modifier = Modifier.padding(20.dp))
        }
        return
    }

    val percurso = listOf(GeoPoint(pousada.latitude, pousada.longitude)) +
        roteiro.pontos.map { GeoPoint(it.latitude, it.longitude) }
    val marcadores = buildList {
        add(MarcadorMapa("0", pousada.latitude, pousada.longitude, EstiloMarcador.INICIO, "P", Azul))
        roteiro.pontos.forEachIndexed { i, p ->
            add(MarcadorMapa("${i + 1}", p.latitude, p.longitude, EstiloMarcador.NUMERO, "${i + 1}", Laranja))
        }
    }

    Column(Modifier.fillMaxSize()) {
        // Mapa do percurso
        // clipToBounds: o mapa não desenha por cima do texto de baixo
        Box(Modifier.fillMaxWidth().height(260.dp).clipToBounds()) {
            MapaOsm(
                marcadores = marcadores,
                enquadrar = percurso,
                chaveEnquadramento = roteiro.id,
                linha = percurso,
                modifier = Modifier.fillMaxSize()
            )
            Box(Modifier.statusBarsPadding().padding(16.dp)) { BotaoVoltar(onVoltar) }
        }

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column {
                EtiquetaTriangulo(stringResource(R.string.rotd_etiqueta), cor = Laranja)
                Spacer(Modifier.height(8.dp))
                TituloDestaque(roteiro.nome, tamanho = 19)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Estatistica(formatarKm(roteiro.distanciaKm), stringResource(R.string.rotd_distancia), Modifier.weight(1f))
                Estatistica("${roteiro.duracaoMinutos} min", stringResource(R.string.rotd_duracao), Modifier.weight(1f))
                Estatistica("${roteiro.pontos.size}", stringResource(R.string.rotd_paragens), Modifier.weight(1f))
            }

            Column {
                LinhaParagem(0, pousada.nome, stringResource(R.string.rotd_partida), ultima = false)
                roteiro.pontos.forEachIndexed { i, p ->
                    LinhaParagem(
                        i + 1, p.nome, "${p.descricao} · ${p.minutos} min",
                        ultima = i == roteiro.pontos.lastIndex
                    )
                }
            }
        }

        BotaoAmarelo(
            stringResource(R.string.rotd_abrir_maps),
            onClick = {
                abrirRoteiroNoMapa(
                    context,
                    pousada.latitude to pousada.longitude,
                    roteiro.pontos.map { it.latitude to it.longitude }
                )
            },
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
private fun Estatistica(valor: String, legenda: String, modifier: Modifier) {
    CaixaBranca(modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(valor, fontFamily = Unbounded, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Azul)
            Text(legenda.uppercase(), style = MaterialTheme.typography.labelSmall, color = Cinzento)
        }
    }
}

/** Uma paragem da linha do tempo: círculo numerado, linha vertical e texto. */
@Composable
private fun LinhaParagem(numero: Int, nome: String, descricao: String, ultima: Boolean) {
    Row(Modifier.height(IntrinsicSize.Min)) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            CirculoNumero(numero, if (numero == 0) Azul else Laranja)
            if (!ultima) {
                Box(
                    Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(Linha)
                )
            }
        }
        Spacer(Modifier.width(14.dp))
        Column(
            Modifier
                .weight(1f)
                .padding(bottom = 18.dp)
        ) {
            Text(nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoEscuro)
            Text(descricao, style = MaterialTheme.typography.bodySmall, color = Cinzento)
        }
    }
}

@Preview(name = "Roteiro a pé", showSystemUi = true)
@Composable
private fun RoteiroAPePreview() {
    JuventudeExploreTheme(escuro = false) {
        RoteiroDetalheScreen(roteiroId = 5, onVoltar = {})
    }
}
