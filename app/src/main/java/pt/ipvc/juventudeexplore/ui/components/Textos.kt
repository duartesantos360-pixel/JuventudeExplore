package pt.ipvc.juventudeexplore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.AzulClaro
import pt.ipvc.juventudeexplore.ui.theme.Laranja
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.Unbounded
import pt.ipvc.juventudeexplore.util.formatarPreco

/** Título com caixa colorida por trás (como o título laranja da página inicial do site). */
@Composable
fun TituloDestaque(texto: String, fundo: Color = Laranja, cor: Color = Color.White, tamanho: Int = 22) {
    Text(
        texto,
        fontFamily = Unbounded,
        fontWeight = FontWeight.Bold,
        fontSize = tamanho.sp,
        lineHeight = (tamanho + 6).sp,
        color = cor,
        modifier = Modifier
            .background(fundo)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

/** Etiqueta "▲ POUSADAS" dos cartões do site. */
@Composable
fun EtiquetaTriangulo(texto: String, cor: Color = Azul) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Triangulo(cor, 12.dp)
        Spacer(Modifier.width(6.dp))
        Text(texto.uppercase(), color = cor, style = MaterialTheme.typography.labelSmall)
    }
}

/** Caixa azul "DESDE 18,00€ a noite". */
@Composable
fun CaixaPreco(preco: Double, modifier: Modifier = Modifier, grande: Boolean = false) {
    Row(
        modifier
            .background(Azul)
            .padding(horizontal = 12.dp, vertical = if (grande) 10.dp else 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.comp_desde),
            color = Color.White,
            fontFamily = Unbounded,
            fontWeight = FontWeight.Bold,
            fontSize = if (grande) 12.sp else 10.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(Modifier.width(6.dp))
        Text(
            formatarPreco(preco),
            color = Color.White,
            fontFamily = Unbounded,
            fontWeight = FontWeight.Bold,
            fontSize = if (grande) 22.sp else 16.sp
        )
        Spacer(Modifier.width(4.dp))
        Text(stringResource(R.string.comp_a_noite), color = Color.White, fontSize = if (grande) 13.sp else 11.sp)
    }
}

/** Etiqueta pequena (ex.: "Acessível"). */
@Composable
fun Etiqueta(texto: String, fundo: Color = AzulClaro, cor: Color = TextoEscuro) {
    Text(
        texto.uppercase(),
        modifier = Modifier
            .background(fundo)
            .padding(horizontal = 6.dp, vertical = 3.dp),
        color = cor,
        style = MaterialTheme.typography.labelSmall,
        fontSize = 10.sp
    )
}

/** Título de secção em maiúsculas espaçadas. */
@Composable
fun TituloSecao(texto: String, modifier: Modifier = Modifier) {
    Text(
        texto.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        letterSpacing = 2.sp,
        color = TextoEscuro,
        modifier = modifier.semantics { heading() }
    )
}
