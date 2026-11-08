package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.Turquesa

/** Filtros por cima da lista (por agora só mudam o que se vê, com os dados de exemplo). */
private enum class Filtro(val titulo: Int) {
    TODAS(R.string.filtro_todas),
    MAIS_BARATAS(R.string.filtro_baratas),
    ACESSIVEIS(R.string.filtro_acessiveis)
}

@Composable
fun ExplorarScreen(
    onAbrirPousada: (Int) -> Unit,
    onReservar: (Int) -> Unit,
    onVerMapa: () -> Unit
) {
    var pesquisa by rememberSaveable { mutableStateOf("") }
    var filtro by rememberSaveable { mutableStateOf(Filtro.TODAS) }

    val encontradas = DadosExemplo.pousadas.filter {
        pesquisa.isBlank() || it.cidade.contains(pesquisa.trim(), ignoreCase = true) ||
            it.nome.contains(pesquisa.trim(), ignoreCase = true)
    }
    val pousadas = when (filtro) {
        Filtro.TODAS -> encontradas
        Filtro.MAIS_BARATAS -> encontradas.sortedBy { it.precos.desde }
        Filtro.ACESSIVEIS -> encontradas.filter { it.acessivel }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { BarraTopo() }
        item { Destaque(pesquisa, onPesquisa = { pesquisa = it }) }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(Filtro.entries) { f ->
                    ChipFiltro(stringResource(f.titulo), filtro == f) { filtro = f }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TituloSecao(stringResource(R.string.expl_n_pousadas, pousadas.size), Modifier.weight(1f))
                Row(
                    Modifier
                        .minimumInteractiveComponentSize()
                        .clickable(role = Role.Button, onClick = onVerMapa),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.width(16.dp).height(2.dp).background(TextoEscuro))
                    Spacer(Modifier.width(6.dp))
                    Text(stringResource(R.string.expl_ver_mapa), style = MaterialTheme.typography.labelSmall, color = TextoEscuro)
                }
            }
        }

        if (pousadas.isEmpty()) {
            item {
                Text(
                    stringResource(R.string.expl_sem_resultados),
                    color = Cinzento,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }

        items(pousadas, key = { it.id }) { pousada ->
            Box(Modifier.padding(horizontal = 20.dp)) {
                CartaoPousada(
                    pousada = pousada,
                    onClick = { onAbrirPousada(pousada.id) },
                    onReservar = { onReservar(pousada.id) }
                )
            }
        }
    }
}

/** Barra branca do topo com o logótipo, como no site. */
@Composable
private fun BarraTopo() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Branco)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Logotipo(40.dp)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text("JUVENTUDE", style = MaterialTheme.typography.labelSmall, color = TextoEscuro)
            Text("Explore", style = MaterialTheme.typography.titleLarge, color = Azul)
        }
        Icon(Icons.Outlined.Notifications, contentDescription = stringResource(R.string.expl_notificacoes), tint = Azul)
    }
}

/**
 * Como a página inicial do site: zona azul com o título em caixas laranja e, por baixo,
 * fundo turquesa com linhas topográficas onde fica a pesquisa.
 */
@Composable
private fun Destaque(pesquisa: String, onPesquisa: (String) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Box(Modifier.fillMaxWidth().background(Azul)) {
            LinhasTopograficas(Modifier.matchParentSize())
            Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 22.dp)) {
                TituloDestaque(stringResource(R.string.expl_titulo_1))
                TituloDestaque(stringResource(R.string.expl_titulo_2))
                Spacer(Modifier.height(12.dp))
                Text(
                    stringResource(R.string.expl_slogan),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
        Box(Modifier.fillMaxWidth().background(Turquesa)) {
            LinhasTopograficas(Modifier.matchParentSize(), cor = Color.White.copy(alpha = 0.5f))
            Column(Modifier.padding(horizontal = 16.dp, vertical = 18.dp)) {
                Text(
                    stringResource(R.string.barra_escolhe).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextoEscuro
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = onPesquisa,
                    placeholder = { Text(stringResource(R.string.barra_para_onde), color = Cinzento) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Azul) },
                    singleLine = true,
                    shape = RectangleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Branco,
                        unfocusedContainerColor = Branco,
                        focusedBorderColor = Azul,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
