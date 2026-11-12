package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.AzulClaro
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro

@Composable
fun PerfilScreen(
    onAbrirPousada: (Int) -> Unit,
    onReservar: (Int) -> Unit,
    onEntrar: () -> Unit
) {
    // Favoritas de exemplo (ainda não são guardadas)
    val favoritas = listOfNotNull(DadosExemplo.pousada(30), DadosExemplo.pousada(20))
    // 0 = igual ao telemóvel, 1 = claro, 2 = escuro (por agora só muda o botão escolhido)
    var aparencia by rememberSaveable { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Cabecalho(stringResource(R.string.menu_perfil)) }

        // Conta
        item {
            Column(
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(Branco)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(AzulClaro),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = Azul)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            stringResource(R.string.perfil_ola_viajante),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )
                        Text(
                            stringResource(R.string.perfil_n_favoritas, favoritas.size),
                            style = MaterialTheme.typography.bodySmall,
                            color = Cinzento
                        )
                    }
                }
                Text(stringResource(R.string.perfil_sem_conta), style = MaterialTheme.typography.bodySmall, color = Cinzento)
                BotaoAzul(stringResource(R.string.perfil_entrar), onClick = onEntrar, modifier = Modifier.fillMaxWidth())
            }
        }

        // Aparência
        item {
            Column(
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(Branco)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(stringResource(R.string.perfil_aparencia), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoEscuro)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipFiltro(stringResource(R.string.tema_sistema), aparencia == 0) { aparencia = 0 }
                    ChipFiltro(stringResource(R.string.tema_claro), aparencia == 1) { aparencia = 1 }
                    ChipFiltro(stringResource(R.string.tema_escuro), aparencia == 2) { aparencia = 2 }
                }
            }
        }

        // Favoritas
        item {
            TituloSecao(stringResource(R.string.perfil_favoritas), Modifier.padding(horizontal = 20.dp, vertical = 4.dp))
        }
        items(favoritas, key = { it.id }) { pousada ->
            Box(Modifier.padding(horizontal = 20.dp)) {
                CartaoPousada(
                    pousada = pousada,
                    onClick = { onAbrirPousada(pousada.id) },
                    onReservar = { onReservar(pousada.id) }
                )
            }
        }

        item {
            Text(
                stringResource(R.string.perfil_rodape),
                style = MaterialTheme.typography.bodySmall,
                color = Cinzento,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(name = "Perfil", showSystemUi = true)
@Composable
private fun PerfilPreview() {
    JuventudeExploreTheme(escuro = false) {
        PerfilScreen(onAbrirPousada = {}, onReservar = {}, onEntrar = {})
    }
}
