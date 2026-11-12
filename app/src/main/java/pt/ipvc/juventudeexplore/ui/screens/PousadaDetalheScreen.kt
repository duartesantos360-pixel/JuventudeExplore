package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.data.Pousada
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.Laranja
import pt.ipvc.juventudeexplore.ui.theme.Linha
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.Unbounded

@Composable
fun PousadaDetalheScreen(
    pousadaId: Int,
    onVoltar: () -> Unit,
    onComoChegar: (Int) -> Unit,
    onAbrirRoteiro: (Int) -> Unit,
    onReservar: (Int) -> Unit
) {
    val pousada = DadosExemplo.pousada(pousadaId)
    // Por agora o coração só muda neste ecrã (ainda não fica guardado)
    var favorito by rememberSaveable { mutableStateOf(false) }

    if (pousada == null) {
        Column(Modifier.fillMaxSize().statusBarsPadding()) {
            Cabecalho(stringResource(R.string.det_pousada), onVoltar)
            Text(stringResource(R.string.det_nao_encontrada), modifier = Modifier.padding(20.dp))
        }
        return
    }

    val roteiro = DadosExemplo.roteiroDaPousada(pousada.id)

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Fotografia com os botões por cima
            Box {
                FotoPousada(pousada, Modifier.fillMaxWidth().height(250.dp), tamanhoIcone = 56.dp)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BotaoVoltar(onVoltar)
                    BotaoRedondo(
                        if (favorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        stringResource(if (favorito) R.string.det_remover_favorito else R.string.det_adicionar_favorito),
                        { favorito = !favorito },
                        tint = if (favorito) Laranja else Azul
                    )
                }
                CaixaPreco(pousada.precos.desde, Modifier.align(Alignment.BottomStart), grande = true)
            }

            // Caixa branca com o nome
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Branco)
                    .padding(20.dp)
            ) {
                EtiquetaTriangulo(stringResource(R.string.det_pousada_cidade, pousada.cidade))
                Spacer(Modifier.height(6.dp))
                Text(pousada.nome, style = MaterialTheme.typography.headlineSmall, color = TextoEscuro)
                Text(pousada.morada.uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Normal, color = Cinzento)
                Spacer(Modifier.height(10.dp))
                Text(pousada.descricao, style = MaterialTheme.typography.bodyMedium, color = TextoEscuro)
            }

            Column(
                Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotaoAmarelo(
                    stringResource(R.string.barra_reservar),
                    onClick = { onReservar(pousada.id) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(4.dp))
                SecaoPrecos(pousada)

                Spacer(Modifier.height(4.dp))
                SecaoAcessibilidade(pousada)
            }
        }

        // Botões fixos em baixo
        Row(
            Modifier
                .fillMaxWidth()
                .background(Branco)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BotaoAzul(
                stringResource(R.string.det_como_chegar),
                onClick = { onComoChegar(pousada.id) },
                modifier = Modifier.weight(1f),
                icone = Icons.Filled.DirectionsBus
            )
            BotaoContorno(
                stringResource(R.string.det_roteiro),
                onClick = { roteiro?.let { onAbrirRoteiro(it.id) } },
                enabled = roteiro != null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SecaoPrecos(pousada: Pousada) {
    TituloSecao(stringResource(R.string.det_precos))
    CaixaBranca(Modifier.fillMaxWidth()) {
        Column {
            LinhaPreco(stringResource(R.string.det_preco_dormitorio), pousada.precos.dormitorio)
            HorizontalDivider(color = Linha, modifier = Modifier.padding(horizontal = 14.dp))
            LinhaPreco(stringResource(R.string.quarto_duplo), pousada.precos.duplo)
            HorizontalDivider(color = Linha, modifier = Modifier.padding(horizontal = 14.dp))
            LinhaPreco(stringResource(R.string.quarto_privado), pousada.precos.privado)
        }
    }
    Text(stringResource(R.string.det_precos_nota), style = MaterialTheme.typography.bodySmall, color = Cinzento)
}

@Composable
private fun LinhaPreco(tipo: String, preco: Double?) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(tipo, color = TextoEscuro, modifier = Modifier.weight(1f))
        if (preco == null) {
            Text(stringResource(R.string.det_nao_existe), style = MaterialTheme.typography.bodySmall, color = Cinzento)
        } else {
            Text(formatarPreco(preco), color = Azul, fontFamily = Unbounded, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SecaoAcessibilidade(pousada: Pousada) {
    TituloSecao(stringResource(R.string.det_acessibilidade))
    val a = pousada.acessibilidade
    if (a == null) {
        Text(stringResource(R.string.det_sem_acessibilidade), style = MaterialTheme.typography.bodyMedium, color = Cinzento)
    } else {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ItemAcessibilidade(stringResource(R.string.det_sem_degraus), a.entradaSemDegraus, Modifier.weight(1f))
            ItemAcessibilidade(stringResource(R.string.det_elevador), a.elevador, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ItemAcessibilidade(stringResource(R.string.det_quarto_adaptado), a.quartoAdaptado, Modifier.weight(1f))
            ItemAcessibilidade(stringResource(R.string.det_wc_adaptado), a.wcAdaptado, Modifier.weight(1f))
        }
    }
}

@Composable
private fun ItemAcessibilidade(texto: String, disponivel: Boolean, modifier: Modifier) {
    CaixaBranca(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (disponivel) Icons.Filled.Check else Icons.Filled.Close,
                contentDescription = stringResource(if (disponivel) R.string.det_disponivel else R.string.det_nao_disponivel),
                tint = if (disponivel) Azul else Cinzento,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(texto, style = MaterialTheme.typography.bodySmall, color = if (disponivel) TextoEscuro else Cinzento)
        }
    }
}

@Preview(name = "Ficha da pousada", showSystemUi = true)
@Composable
private fun FichaDaPousadaPreview() {
    JuventudeExploreTheme(escuro = false) {
        PousadaDetalheScreen(pousadaId = 38, onVoltar = {}, onComoChegar = {}, onAbrirRoteiro = {}, onReservar = {})
    }
}
