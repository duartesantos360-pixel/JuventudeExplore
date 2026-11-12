package pt.ipvc.juventudeexplore.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.ui.theme.Azul

/** Triângulo azul do logótipo das Pousadas de Juventude. */
@Composable
fun Triangulo(cor: Color = Azul, tamanho: Dp = 14.dp) {
    Canvas(Modifier.size(tamanho)) {
        val p = Path().apply {
            moveTo(size.width / 2, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(p, cor)
    }
}

/** Logótipo da app: triângulo dentro de um círculo branco, como o do site. */
@Composable
fun Logotipo(tamanho: Dp = 44.dp) {
    Box(
        Modifier
            .size(tamanho)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.padding(bottom = 3.dp)) { Triangulo(Azul, tamanho * 0.5f) }
    }
}

/** Linhas topográficas brancas por cima de um fundo (como os fundos turquesa e azul do site). */
@Composable
fun LinhasTopograficas(modifier: Modifier = Modifier, cor: Color = Color.White.copy(alpha = 0.35f)) {
    Canvas(modifier.fillMaxSize()) {
        val centros = listOf(
            Offset(size.width * 0.15f, size.height * 0.3f),
            Offset(size.width * 0.85f, size.height * 0.75f),
            Offset(size.width * 0.6f, size.height * 0.05f)
        )
        centros.forEachIndexed { i, c ->
            for (n in 1..7) {
                val l = n * size.minDimension * 0.09f * (1f + i * 0.15f)
                val a = l * (0.75f + 0.08f * (n % 3))
                drawOval(
                    cor,
                    topLeft = Offset(c.x - l, c.y - a),
                    size = Size(l * 2, a * 2),
                    style = Stroke(width = 2f)
                )
            }
        }
    }
}

/** Fotografia da pousada (com um desenho por baixo enquanto carrega). */
@Composable
fun FotoPousada(pousada: Pousada, modifier: Modifier = Modifier, tamanhoIcone: Dp = 32.dp) {
    Box(
        modifier = modifier.background(Color(pousada.corFoto)),
        contentAlignment = Alignment.Center
    ) {
        LinhasTopograficas(cor = Color.White.copy(alpha = 0.6f))
        Icon(
            Icons.Filled.Hotel,
            contentDescription = null,
            tint = Azul.copy(alpha = 0.45f),
            modifier = Modifier.size(tamanhoIcone)
        )
        pousada.fotoUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = stringResource(R.string.comp_fotografia, pousada.nome),
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}
