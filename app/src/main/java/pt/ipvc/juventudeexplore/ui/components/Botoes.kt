package pt.ipvc.juventudeexplore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.ui.theme.Amarelo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.TextoSobreClaro

/** Filtro retangular: azul quando selecionado, branco com contorno azul quando não. */
@Composable
fun ChipFiltro(texto: String, selecionado: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RectangleShape,
        color = if (selecionado) Azul else Branco,
        border = BorderStroke(1.5.dp, Azul)
    ) {
        Text(
            texto.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            color = if (selecionado) Color.White else Azul,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/** Botão branco redondo (voltar, favorito) usado por cima de imagens e mapas. */
@Composable
fun BotaoRedondo(icone: ImageVector, descricao: String, onClick: () -> Unit, tint: Color = Azul) {
    Surface(onClick = onClick, shape = CircleShape, color = Branco, shadowElevation = 3.dp) {
        Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
            Icon(icone, contentDescription = descricao, tint = tint)
        }
    }
}

@Composable
fun BotaoVoltar(onClick: () -> Unit) {
    BotaoRedondo(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.comp_voltar), onClick)
}

/** Botão amarelo com texto escuro espaçado, como o "RESERVAR" do site. */
@Composable
fun BotaoAmarelo(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    conteudoExtra: @Composable RowScope.() -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Amarelo, contentColor = TextoSobreClaro),
        modifier = modifier.heightIn(min = 54.dp)
    ) {
        Text(texto.uppercase(), style = MaterialTheme.typography.labelLarge)
        conteudoExtra()
    }
}

/** Botão azul cheio. */
@Composable
fun BotaoAzul(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icone: ImageVector? = null
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Azul, contentColor = Color.White),
        modifier = modifier.heightIn(min = 54.dp)
    ) {
        if (icone != null) {
            Icon(icone, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(texto.uppercase(), style = MaterialTheme.typography.labelLarge)
    }
}

/** Botão secundário com contorno azul. */
@Composable
fun BotaoContorno(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        border = BorderStroke(2.dp, if (enabled) Azul else Cinzento.copy(alpha = 0.3f)),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Branco, contentColor = Azul),
        modifier = modifier.heightIn(min = 54.dp)
    ) {
        Text(texto.uppercase(), style = MaterialTheme.typography.labelLarge)
    }
}
