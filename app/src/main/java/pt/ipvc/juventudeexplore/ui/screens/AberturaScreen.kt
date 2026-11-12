package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.ui.theme.JuventudeExploreTheme

/** Verde do primeiro ecrã do sistema (o mesmo de res/values/cores_logo.xml). */
private val VerdeAbertura = Color(0xFF5BC08F)

/**
 * Ecrã de abertura: fundo verde com o padrão do site das Pousadas e o logótipo animado
 * (o marcador cresce e o triângulo cai para o sítio). Dura pouco mais de 1 segundo.
 * Começa igual ao primeiro ecrã do sistema (verde liso e o logótipo de res/drawable/logo_abertura.xml)
 * e o padrão aparece devagar por cima, para não se notar a troca.
 */
@Composable
fun AberturaScreen(onFim: () -> Unit) {
    val padrao = ImageBitmap.imageResource(R.drawable.padrao_verde)
    val fundo = remember(padrao) { ShaderBrush(ImageShader(padrao, TileMode.Repeated, TileMode.Repeated)) }

    val escala = remember { Animatable(0.7f) }
    // Deslocamento do triângulo em unidades do desenho (o logótipo tem 108 de altura).
    val queda = remember { Animatable(-14f) }
    val padraoVisivel = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch { padraoVisivel.animateTo(1f, tween(350)) }
        launch { escala.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)) }
        delay(300)
        queda.animateTo(0f, spring(dampingRatio = 0.4f, stiffness = Spring.StiffnessMediumLow))
        delay(400)
        onFim()
    }

    Box(
        Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(VerdeAbertura)
                drawRect(fundo, alpha = padraoVisivel.value)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(220.dp)
                .graphicsLayer {
                    scaleX = escala.value
                    scaleY = escala.value
                    // Cresce a partir da ponta do marcador.
                    transformOrigin = TransformOrigin(0.5f, 84f / 108f)
                }
        ) {
            Image(painterResource(R.drawable.logo_pino), contentDescription = null, modifier = Modifier.fillMaxSize())
            Image(
                painterResource(R.drawable.logo_triangulo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { translationY = queda.value / 108f * size.height }
            )
        }
    }
}

@Preview(name = "Abertura", showSystemUi = true)
@Composable
private fun AberturaPreview() {
    JuventudeExploreTheme(escuro = false) {
        AberturaScreen(onFim = {})
    }
}
