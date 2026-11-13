package pt.ipvc.juventudeexplore.ui.screens.roteiros

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.data.model.Regiao
import pt.ipvc.juventudeexplore.data.model.Roteiro
import pt.ipvc.juventudeexplore.ui.components.Cabecalho
import pt.ipvc.juventudeexplore.ui.components.ChipFiltro
import pt.ipvc.juventudeexplore.ui.components.EtiquetaTriangulo
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.Laranja
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.util.formatarKm

@Composable
fun RoteirosScreen(
    roteiros: List<Roteiro>,       // roteiros ja filtrados pelo ViewModel
    regioes: List<Regiao>,         // regioes que aparecem nos botoes
    regiao: Regiao?,               // regiao escolhida (null = todas)
    onRegiao: (Regiao?) -> Unit,
    onAbrirRoteiro: (Int) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Cabecalho(stringResource(R.string.rot_titulo), subtitulo = stringResource(R.string.rot_subtitulo))
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { ChipFiltro(stringResource(R.string.rot_todas_regioes), regiao == null) { onRegiao(null) } }
                    items(regioes) { r ->
                        ChipFiltro(stringResource(r.titulo), regiao == r) { onRegiao(r) }
                    }
                }
            }
            items(roteiros, key = { it.id }) { roteiro ->
                Surface(
                    onClick = { onAbrirRoteiro(roteiro.id) },
                    shape = RectangleShape,
                    color = Branco,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        EtiquetaTriangulo(roteiro.cidade, cor = Laranja)
                        Spacer(Modifier.height(4.dp))
                        Text(roteiro.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoEscuro)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            stringResource(
                                R.string.rot_resumo,
                                formatarKm(roteiro.distanciaKm),
                                roteiro.duracaoMinutos,
                                roteiro.pontos.size
                            ).uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Normal,
                            color = Cinzento
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Roteiros", showSystemUi = true)
@Composable
private fun RoteirosPreview() {
    JuventudeExploreTheme(escuro = false) {
        RoteirosScreen(
            roteiros = DadosExemplo.roteiros,
            regioes = Regiao.entries,
            regiao = null,
            onRegiao = {},
            onAbrirRoteiro = {}
        )
    }
}