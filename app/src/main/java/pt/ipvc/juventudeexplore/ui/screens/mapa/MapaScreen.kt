package pt.ipvc.juventudeexplore.ui.screens.mapa

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.osmdroid.util.GeoPoint
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.data.model.Roteiro
import pt.ipvc.juventudeexplore.ui.components.BotaoRedondo
import pt.ipvc.juventudeexplore.ui.components.CaixaPreco
import pt.ipvc.juventudeexplore.ui.components.ChipFiltro
import pt.ipvc.juventudeexplore.ui.components.EstiloMarcador
import pt.ipvc.juventudeexplore.ui.components.EtiquetaTriangulo
import pt.ipvc.juventudeexplore.ui.components.FotoPousada
import pt.ipvc.juventudeexplore.ui.components.MapaOsm
import pt.ipvc.juventudeexplore.ui.components.MarcadorMapa
import pt.ipvc.juventudeexplore.ui.theme.Amarelo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.TextoSobreClaro
import pt.ipvc.juventudeexplore.util.formatarPreco

@Composable
fun MapaScreen(
    pousadas: List<Pousada>,
    roteiros: List<Roteiro>,
    pesquisa: String,
    onPesquisa: (String) -> Unit,
    onAbrirPousada: (Int) -> Unit,
    onReservar: (Int) -> Unit,
    onVerLista: () -> Unit
) {
    // A pesquisa e as listas vem do ViewModel (partilhadas com o Explorar).
    // Aqui so fica o que e do proprio mapa: a pousada escolhida e a camada do patrimonio.
    var selecionadaId by rememberSaveable { mutableStateOf<Int?>(null) }
    var verPatrimonio by rememberSaveable { mutableStateOf(true) }

    val selecionada = pousadas.find { it.id == selecionadaId }

    val marcadores = buildList {
        if (verPatrimonio) {
            roteiros.filter { r -> pousadas.any { it.id == r.pousadaId } }.forEach { r ->
                r.pontos.forEachIndexed { i, p ->
                    add(MarcadorMapa("ponto:${r.id}:$i", p.latitude, p.longitude, EstiloMarcador.PATRIMONIO))
                }
            }
        }
        pousadas.forEach { p ->
            add(
                MarcadorMapa(
                    "pousada:${p.id}", p.latitude, p.longitude,
                    if (p.id == selecionadaId) EstiloMarcador.PRECO_SELECIONADO else EstiloMarcador.PRECO,
                    texto = formatarPreco(p.precos.desde)
                )
            )
        }
    }

    // Com uma pousada escolhida o mapa aproxima-se dela; sem nenhuma, mostra todas
    val enquadrar = if (selecionada != null) {
        listOf(GeoPoint(selecionada.latitude, selecionada.longitude))
    } else {
        pousadas.map { GeoPoint(it.latitude, it.longitude) }
    }

    Box(Modifier.fillMaxSize()) {
        MapaOsm(
            marcadores = marcadores,
            enquadrar = enquadrar,
            chaveEnquadramento = listOf(pesquisa, selecionadaId),
            modifier = Modifier.fillMaxSize(),
            zoomMaximo = 14.0,
            onMarcador = { id ->
                if (id.startsWith("pousada:")) selecionadaId = id.removePrefix("pousada:").toInt()
            }
        )

        // Pesquisa e camadas por cima do mapa
        Column(
            Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BotaoRedondo(Icons.AutoMirrored.Filled.List, stringResource(R.string.mapa_ver_lista), onVerLista)
                Spacer(Modifier.width(10.dp))
                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = {
                        onPesquisa(it)
                        selecionadaId = null
                    },
                    placeholder = { Text(stringResource(R.string.mapa_pesquisa), color = Cinzento) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Azul) },
                    singleLine = true,
                    shape = RectangleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Branco,
                        unfocusedContainerColor = Branco,
                        focusedBorderColor = Azul,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChipFiltro(stringResource(R.string.camada_pousadas), true) { }
                ChipFiltro(stringResource(R.string.camada_patrimonio), verPatrimonio) { verPatrimonio = !verPatrimonio }
            }
        }

        // Cartão da pousada selecionada
        if (selecionada != null) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                CartaoPousadaMapa(
                    pousada = selecionada,
                    onVerFicha = { onAbrirPousada(selecionada.id) },
                    onReservar = { onReservar(selecionada.id) },
                    onFechar = { selecionadaId = null }
                )
            }
        }
    }
}

@Composable
private fun CartaoPousadaMapa(
    pousada: Pousada,
    onVerFicha: () -> Unit,
    onReservar: () -> Unit,
    onFechar: () -> Unit
) {
    Surface(
        shape = RectangleShape,
        color = Branco,
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(Modifier.padding(start = 12.dp, top = 12.dp)) {
                FotoPousada(pousada, Modifier.size(64.dp), tamanhoIcone = 24.dp)
                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    EtiquetaTriangulo(pousada.cidade)
                    Text(pousada.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoEscuro)
                    val detalhes = listOfNotNull(
                        pousada.paragens.firstOrNull()?.nome,
                        if (pousada.acessivel) stringResource(R.string.comp_acessivel) else null
                    ).joinToString(" · ")
                    if (detalhes.isNotEmpty()) {
                        Text(detalhes, style = MaterialTheme.typography.bodySmall, color = Cinzento)
                    }
                    Text(
                        stringResource(R.string.mapa_ver_ficha),
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .minimumInteractiveComponentSize()
                            .clickable(role = Role.Button, onClick = onVerFicha)
                    )
                }
                IconButton(onClick = onFechar) {
                    Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.mapa_fechar), tint = Cinzento)
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onReservar),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CaixaPreco(pousada.precos.desde, Modifier.weight(1f))
                Text(
                    stringResource(R.string.comp_reservar),
                    style = MaterialTheme.typography.labelLarge,
                    color = TextoSobreClaro,
                    modifier = Modifier
                        .background(Amarelo)
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Preview(name = "Mapa", showSystemUi = true)
@Composable
private fun MapaPreview() {
    JuventudeExploreTheme(escuro = false) {
        MapaScreen(
            pousadas = DadosExemplo.pousadas,
            roteiros = DadosExemplo.roteiros,
            pesquisa = "",
            onPesquisa = {},
            onAbrirPousada = {},
            onReservar = {},
            onVerLista = {}
        )
    }
}