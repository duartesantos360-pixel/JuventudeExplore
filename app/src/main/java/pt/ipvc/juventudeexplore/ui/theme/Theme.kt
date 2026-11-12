package pt.ipvc.juventudeexplore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val CoresClaras = lightColorScheme(
    primary = Azul,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCFEEF5),
    onPrimaryContainer = Color(0xFF231F20),
    secondary = Amarelo,
    onSecondary = TextoSobreClaro,
    tertiary = Laranja,
    onTertiary = Color.White,
    background = Color(0xFFEEF6FC),
    onBackground = Color(0xFF231F20),
    surface = Color(0xFFEEF6FC),
    onSurface = Color(0xFF231F20),
    surfaceVariant = Color.White,
    onSurfaceVariant = Color(0xFF6F6B6C),
    outline = Azul,
    outlineVariant = Color(0xFFDCE9F2)
)

private val CoresEscuras = darkColorScheme(
    primary = Azul,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1D3B4D),
    onPrimaryContainer = Color(0xFFE9EEF2),
    secondary = Amarelo,
    onSecondary = TextoSobreClaro,
    tertiary = Laranja,
    onTertiary = Color.White,
    background = Color(0xFF10181F),
    onBackground = Color(0xFFE9EEF2),
    surface = Color(0xFF10181F),
    onSurface = Color(0xFFE9EEF2),
    surfaceVariant = Color(0xFF1B252E),
    onSurfaceVariant = Color(0xFFA9B3BB),
    outline = Azul,
    outlineVariant = Color(0xFF2C3A45),
    surfaceContainerHigh = Color(0xFF1B252E),
    surfaceContainerHighest = Color(0xFF22303B)
)

// O site usa formas retas, sem cantos arredondados
private val Formas = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)

/** Tema da app; [escuro] escolhe o modo escuro (por defeito igual ao do telemóvel). */
@Composable
fun JuventudeExploreTheme(escuro: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalModoEscuro provides escuro) {
        MaterialTheme(
            colorScheme = if (escuro) CoresEscuras else CoresClaras,
            typography = Tipografia,
            shapes = Formas,
            content = content
        )
    }
}
