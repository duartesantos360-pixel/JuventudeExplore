package pt.ipvc.juventudeexplore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.ui.theme.Amarelo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.TextoSobreClaro

/** Cabeçalho branco dos ecrãs (título + voltar opcional). */
@Composable
fun Cabecalho(titulo: String, onVoltar: (() -> Unit)? = null, subtitulo: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Branco)
            .padding(start = 8.dp, end = 20.dp, top = 12.dp, bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onVoltar != null) {
            IconButton(onClick = onVoltar) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.comp_voltar), tint = Azul)
            }
        } else {
            Spacer(Modifier.width(12.dp))
        }
        Column {
            Text(titulo, style = MaterialTheme.typography.headlineSmall, color = Azul, modifier = Modifier.semantics { heading() })
            if (subtitulo != null) {
                Text(subtitulo.uppercase(), style = MaterialTheme.typography.labelSmall, color = TextoEscuro)
            }
        }
    }
}

/** Cartão de pousada como os do site: foto, caixa branca com título e caixa azul com o preço. */
@Composable
fun CartaoPousada(
    pousada: Pousada,
    onClick: () -> Unit,
    onReservar: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RectangleShape,
        color = Branco,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box {
                FotoPousada(pousada, Modifier.fillMaxWidth().height(130.dp))
                if (pousada.acessivel) {
                    Box(Modifier.align(Alignment.TopEnd).padding(10.dp)) {
                        Etiqueta(stringResource(R.string.comp_acessivel), fundo = Amarelo, cor = TextoSobreClaro)
                    }
                }
            }
            Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)) {
                EtiquetaTriangulo(pousada.cidade)
                Spacer(Modifier.height(4.dp))
                Text(pousada.nome, style = MaterialTheme.typography.titleLarge, color = TextoEscuro)
                Text(
                    "${pousada.bairro} · ${stringResource(pousada.regiao.titulo)}".uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal,
                    color = Cinzento
                )
            }
            Row(Modifier.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                CaixaPreco(pousada.precos.desde, Modifier.weight(1f))
                Text(
                    stringResource(R.string.comp_reservar),
                    style = MaterialTheme.typography.labelLarge,
                    color = TextoSobreClaro,
                    modifier = Modifier
                        .background(Amarelo)
                        .clickable(onClick = onReservar)
                        .padding(horizontal = 18.dp, vertical = 8.dp)
                )
            }
        }
    }
}

/** Caixa branca retangular para agrupar conteúdo. */
@Composable
fun CaixaBranca(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier.background(Branco)) { content() }
}

/** Círculo com número (paragens do roteiro). */
@Composable
fun CirculoNumero(numero: Int, cor: Color) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(cor),
        contentAlignment = Alignment.Center
    ) {
        Text("$numero", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

/** Contorno azul para destacar a opção escolhida. */
fun Modifier.contornoAzul(ativo: Boolean): Modifier =
    if (ativo) this.border(2.dp, Azul) else this
