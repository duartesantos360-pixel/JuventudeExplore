package pt.ipvc.juventudeexplore.ui.screens.reservas

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.data.model.TipoQuarto
import pt.ipvc.juventudeexplore.ui.components.BotaoAmarelo
import pt.ipvc.juventudeexplore.ui.components.BotaoContorno
import pt.ipvc.juventudeexplore.ui.components.Cabecalho
import pt.ipvc.juventudeexplore.ui.components.Etiqueta
import pt.ipvc.juventudeexplore.ui.components.EtiquetaTriangulo
import pt.ipvc.juventudeexplore.ui.components.LinhasTopograficas
import pt.ipvc.juventudeexplore.ui.components.TituloDestaque
import pt.ipvc.juventudeexplore.ui.components.TituloSecao
import pt.ipvc.juventudeexplore.ui.components.contornoAzul
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.Turquesa
import pt.ipvc.juventudeexplore.ui.theme.Unbounded
import pt.ipvc.juventudeexplore.util.formatarPreco

/**
 * Ecrã de reserva (só o desenho e a navegação): escolher o quarto, escrever os dados e confirmar.
 * Ao confirmar mostra o ecrã de "Reserva confirmada!" (ainda não guarda nada).
 */
@Composable
fun ReservaScreen(pousadaId: Int, onVoltar: () -> Unit, onVerReservas: () -> Unit) {
    val pousada = DadosExemplo.pousada(pousadaId)
    var tipo by rememberSaveable { mutableStateOf(TipoQuarto.DORMITORIO) }
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var confirmada by rememberSaveable { mutableStateOf(false) }

    if (pousada == null) {
        Column(Modifier.fillMaxSize().statusBarsPadding()) {
            Cabecalho(stringResource(R.string.barra_reservar), onVoltar)
            Text(stringResource(R.string.det_nao_encontrada), modifier = Modifier.padding(20.dp))
        }
        return
    }

    if (confirmada) {
        ReservaConfirmada(pousada, tipo, nome, onVerReservas, onVoltar)
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Cabecalho(stringResource(R.string.barra_reservar), onVoltar, subtitulo = pousada.nome)

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TituloSecao(stringResource(R.string.det_tua_estadia))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Branco)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextoData(stringResource(R.string.barra_entrada), "20/11/2026", Modifier.weight(1f))
                TextoData(stringResource(R.string.barra_saida), "22/11/2026", Modifier.weight(1f))
                TextoData(stringResource(R.string.barra_pessoas), "1", Modifier.weight(1f))
            }

            Spacer(Modifier.height(4.dp))
            TituloSecao(stringResource(R.string.res_tipo_quarto))
            TipoQuarto.entries.forEach { t ->
                val preco = precoNoite(pousada, t)
                if (preco != null) {
                    OpcaoQuarto(t, preco, selecionado = t == tipo, onClick = { tipo = t })
                }
            }

            Spacer(Modifier.height(4.dp))
            TituloSecao(stringResource(R.string.res_teus_dados))
            CampoReserva(nome, { nome = it }, stringResource(R.string.res_nome))
            CampoReserva(email, { email = it }, stringResource(R.string.res_email))
        }

        BotaoAmarelo(
            stringResource(R.string.res_confirmar),
            onClick = { confirmada = true },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )
    }
}

private fun precoNoite(pousada: Pousada, tipo: TipoQuarto): Double? = when (tipo) {
    TipoQuarto.DORMITORIO -> pousada.precos.dormitorio
    TipoQuarto.DUPLO -> pousada.precos.duplo
    TipoQuarto.PRIVADO -> pousada.precos.privado
}

@Composable
private fun TextoData(titulo: String, valor: String, modifier: Modifier) {
    Column(modifier) {
        Text(titulo.uppercase(), style = MaterialTheme.typography.labelSmall, color = Azul)
        Text(valor, color = TextoEscuro, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun OpcaoQuarto(tipo: TipoQuarto, preco: Double, selecionado: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RectangleShape,
        color = Branco,
        modifier = Modifier
            .fillMaxWidth()
            .contornoAzul(selecionado)
    ) {
        Row(Modifier.padding(horizontal = 8.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selecionado,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = Azul)
            )
            Column(Modifier.weight(1f)) {
                Text(stringResource(tipo.titulo), color = TextoEscuro, fontWeight = FontWeight.Bold)
                Text(stringResource(tipo.descricao), style = MaterialTheme.typography.bodySmall, color = Cinzento)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(formatarPreco(preco), color = Azul, fontFamily = Unbounded, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.comp_a_noite), style = MaterialTheme.typography.bodySmall, color = Cinzento)
            }
        }
    }
}

@Composable
private fun CampoReserva(valor: String, onValor: (String) -> Unit, rotulo: String) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValor,
        label = { Text(rotulo) },
        singleLine = true,
        shape = RectangleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Branco,
            unfocusedContainerColor = Branco,
            focusedBorderColor = Azul
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

/** Ecrã depois de confirmar a reserva. */
@Composable
private fun ReservaConfirmada(
    pousada: Pousada,
    tipo: TipoQuarto,
    nome: String,
    onVerReservas: () -> Unit,
    onVoltar: () -> Unit
) {
    val total = (precoNoite(pousada, tipo) ?: 0.0) * 2
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(Modifier.fillMaxWidth().background(Azul)) {
            LinhasTopograficas(Modifier.matchParentSize())
            Column(
                Modifier
                    .statusBarsPadding()
                    .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 28.dp)
            ) {
                TituloDestaque(stringResource(R.string.res_confirmada_1))
                TituloDestaque(stringResource(R.string.res_confirmada_2))
            }
        }
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Branco)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                EtiquetaTriangulo(stringResource(R.string.res_codigo_reserva, "JE-2026-0001"))
                Text(pousada.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoEscuro)
                Text("20/11/2026 → 22/11/2026", color = TextoEscuro)
                Text(
                    stringResource(R.string.res_detalhe, stringResource(tipo.titulo)),
                    style = MaterialTheme.typography.bodySmall,
                    color = Cinzento
                )
                if (nome.isNotBlank()) {
                    Text(stringResource(R.string.ress_em_nome, nome), style = MaterialTheme.typography.bodySmall, color = Cinzento)
                }
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        stringResource(R.string.res_total, formatarPreco(total)),
                        fontFamily = Unbounded,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Azul,
                        modifier = Modifier.weight(1f)
                    )
                    Etiqueta(stringResource(R.string.res_pagar_pousada), fundo = Turquesa)
                }
            }
            BotaoAmarelo(stringResource(R.string.res_ver_reservas), onClick = onVerReservas, modifier = Modifier.fillMaxWidth())
            BotaoContorno(stringResource(R.string.comp_voltar), onClick = onVoltar, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(name = "Reservar", showSystemUi = true)
@Composable
private fun ReservarPreview() {
    JuventudeExploreTheme(escuro = false) {
        ReservaScreen(pousadaId = 38, onVoltar = {}, onVerReservas = {})
    }
}

@Preview(name = "Reserva confirmada", showSystemUi = true)
@Composable
private fun ReservaConfirmadaPreview() {
    JuventudeExploreTheme(escuro = false) {
        ReservaConfirmada(
            pousada = DadosExemplo.pousada(38)!!,
            tipo = TipoQuarto.DORMITORIO,
            nome = "Duarte",
            onVerReservas = {},
            onVoltar = {}
        )
    }
}
