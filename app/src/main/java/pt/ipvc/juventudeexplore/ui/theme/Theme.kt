package pt.ipvc.juventudeexplore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Cores tiradas do site pousadasjuventude.pt. As que mudam no modo escuro são lidas com
// LocalModoEscuro (por isso só se podem usar dentro de funções @Composable).
val Azul = Color(0xFF429DDC)          // cor principal (botões, cartões de preço, rodapé)
val Amarelo = Color(0xFFFEB604)       // botão "Reservar"
val Laranja = Color(0xFFF68712)       // caixas de destaque dos títulos

/** true quando a app está em modo escuro (escolhido no Perfil ou igual ao do telemóvel). */
val LocalModoEscuro = staticCompositionLocalOf { false }

/** Cor para o modo claro ou para o escuro, conforme o tema que está a ser usado. */
@Composable
@ReadOnlyComposable
private fun claroOuEscuro(claro: Color, escuro: Color): Color = if (LocalModoEscuro.current) escuro else claro

val AzulEscuro: Color      // azul para texto sobre fundos claros
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFF2F7FB8), Color(0xFF8CC8F0))
val AzulClaro: Color       // faixas claras
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFFCFEEF5), Color(0xFF1D3B4D))
val AzulMuitoClaro: Color  // fundo da app
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFFEEF6FC), Color(0xFF10181F))
val Turquesa: Color        // fundo com linhas topográficas
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFF84E0D7), Color(0xFF1E5B55))
val TextoEscuro: Color     // texto principal (no modo escuro passa a claro)
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFF231F20), Color(0xFFE9EEF2))
val Cinzento: Color
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFF6F6B6C), Color(0xFFA9B3BB))
val Linha: Color
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color(0xFFDCE9F2), Color(0xFF2C3A45))
/** Fundo dos cartões e caixas (branco no modo claro). */
val Branco: Color
    @Composable @ReadOnlyComposable get() = claroOuEscuro(Color.White, Color(0xFF1B252E))

/** Texto escuro que fica sempre escuro, para usar por cima do amarelo. */
val TextoSobreClaro = Color(0xFF231F20)
/** Faixa do aviso "sem internet". */
val SemInternet = Color(0xFF4B5863)

// Cores dos transportes
val AzulMetro = Color(0xFF1C4D88)
val VermelhoAutocarro = Color(0xFFB4452A)
val RoxoComboio = Color(0xFF6A4C93)
val AmareloEletrico = Color(0xFFB7861B)
val Agua = Color(0xFFA9DDF0)

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

private val Tipografia = Typography(
    headlineMedium = TextStyle(fontFamily = Unbounded, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 32.sp),
    headlineSmall = TextStyle(fontFamily = Unbounded, fontWeight = FontWeight.Bold, fontSize = 21.sp, lineHeight = 27.sp),
    titleLarge = TextStyle(fontFamily = Unbounded, fontWeight = FontWeight.Bold, fontSize = 18.sp, lineHeight = 24.sp),
    titleMedium = TextStyle(fontFamily = Unbounded, fontWeight = FontWeight.Medium, fontSize = 15.sp, lineHeight = 21.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 1.5.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Bold, fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = 1.2.sp)
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
