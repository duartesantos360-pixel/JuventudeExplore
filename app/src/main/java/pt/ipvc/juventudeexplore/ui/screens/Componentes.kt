package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.data.model.TipoTransporte
import pt.ipvc.juventudeexplore.ui.theme.Amarelo
import pt.ipvc.juventudeexplore.ui.theme.AmareloEletrico
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.AzulClaro
import pt.ipvc.juventudeexplore.ui.theme.AzulMetro
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento
import pt.ipvc.juventudeexplore.ui.theme.Laranja
import pt.ipvc.juventudeexplore.ui.theme.RoxoComboio
import pt.ipvc.juventudeexplore.ui.theme.TextoEscuro
import pt.ipvc.juventudeexplore.ui.theme.TextoSobreClaro
import pt.ipvc.juventudeexplore.ui.theme.Unbounded
import pt.ipvc.juventudeexplore.ui.theme.VermelhoAutocarro
import pt.ipvc.juventudeexplore.util.formatarPreco

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

@Composable
fun corDoTransporte(tipo: TipoTransporte): Color = when (tipo) {
    TipoTransporte.A_PE -> AzulClaro
    TipoTransporte.METRO -> AzulMetro
    TipoTransporte.AUTOCARRO -> VermelhoAutocarro
    TipoTransporte.ELETRICO -> AmareloEletrico
    TipoTransporte.COMBOIO -> RoxoComboio
    TipoTransporte.BICICLETA -> Azul
}

/** Contorno azul para destacar a opção escolhida. */
fun Modifier.contornoAzul(ativo: Boolean): Modifier =
    if (ativo) this.border(2.dp, Azul) else this

fun letraTransporte(tipo: TipoTransporte): String = when (tipo) {
    TipoTransporte.METRO -> "M"
    TipoTransporte.AUTOCARRO -> "A"
    TipoTransporte.ELETRICO -> "E"
    TipoTransporte.COMBOIO -> "C"
    TipoTransporte.BICICLETA -> "B"
    TipoTransporte.A_PE -> "P"
}
