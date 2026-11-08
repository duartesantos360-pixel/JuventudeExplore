package pt.ipvc.juventudeexplore.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.File
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import pt.ipvc.juventudeexplore.ui.theme.Amarelo
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.AzulMuitoClaro
import pt.ipvc.juventudeexplore.ui.theme.Linha
import pt.ipvc.juventudeexplore.ui.theme.Laranja
import pt.ipvc.juventudeexplore.ui.theme.TextoSobreClaro

/**
 * Mapa real do OpenStreetMap (biblioteca osmdroid, não precisa de chave de API).
 * Os marcadores são desenhados com as cores da app.
 */

enum class EstiloMarcador { PRECO, PRECO_SELECIONADO, PONTO_POUSADA, PARAGEM, PATRIMONIO, NUMERO, INICIO, EU }

/** A partir deste zoom (cidade/região) as pousadas mostram o preço; abaixo, só um ponto azul. */
private const val ZOOM_PRECOS = 9.5

data class MarcadorMapa(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val estilo: EstiloMarcador,
    val texto: String = "",
    val cor: Color = Azul
)

/**
 * @param enquadrar pontos que o mapa deve mostrar; sempre que [chaveEnquadramento] muda o mapa volta a enquadrá-los.
 * @param linha percurso a desenhar (tracejado laranja), por exemplo um roteiro a pé.
 */
@Composable
fun MapaOsm(
    marcadores: List<MarcadorMapa>,
    enquadrar: List<GeoPoint>,
    chaveEnquadramento: Any?,
    modifier: Modifier = Modifier,
    linha: List<GeoPoint> = emptyList(),
    zoomMaximo: Double = 16.0,
    semInternet: Boolean = false,
    onMarcador: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val mapa = remember { criarMapa(context) }
    // Onde ainda não há mapa (ou sem internet) o fundo fica liso, com as cores da app, em vez dos
    // quadrados cinzentos; o percurso, os pontos e a tua posição desenham-se na mesma por cima
    val corFundo = AzulMuitoClaro.toArgb()
    val corLinhas = Linha.toArgb()
    // Com o mapa afastado (país inteiro) os preços ficam pontos, para não se amontoarem
    var afastado by remember { mutableStateOf(mapa.zoomLevelDouble < ZOOM_PRECOS) }
    DisposableEffect(mapa) {
        val ouvinte = object : MapListener {
            override fun onScroll(event: ScrollEvent?) = false
            override fun onZoom(event: ZoomEvent?): Boolean {
                afastado = mapa.zoomLevelDouble < ZOOM_PRECOS
                return false
            }
        }
        mapa.addMapListener(ouvinte)
        onDispose {
            mapa.removeMapListener(ouvinte)
            // Só se desliga o mapa quando o ecrã sai de vez (desligá-lo antes deixa o fundo cinzento)
            mapa.onDetach()
        }
    }

    // Volta a enquadrar quando muda a pesquisa ou a pousada selecionada
    LaunchedEffect(chaveEnquadramento) {
        val acao = Runnable { enquadrarPontos(mapa, enquadrar, zoomMaximo) }
        if (mapa.isLayoutOccurred) acao.run() else mapa.addOnFirstLayoutListener { _, _, _, _, _ -> acao.run() }
    }

    AndroidView(
        factory = { mapa },
        modifier = modifier,
        update = { vista ->
            vista.overlayManager.tilesOverlay.loadingBackgroundColor = corFundo
            vista.overlayManager.tilesOverlay.loadingLineColor = corLinhas
            // Sem internet não vale a pena tentar descarregar mapas
            vista.setUseDataConnection(!semInternet)
            vista.overlays.clear()
            vista.overlays.add(CopyrightOverlay(vista.context))
            if (linha.size >= 2) {
                vista.overlays.add(Polyline(vista).apply {
                    setPoints(linha)
                    outlinePaint.color = Laranja.toArgb()
                    outlinePaint.strokeWidth = 5f * vista.resources.displayMetrics.density
                    outlinePaint.pathEffect = DashPathEffect(floatArrayOf(4f, 14f), 0f)
                    outlinePaint.strokeCap = Paint.Cap.ROUND
                })
            }
            // A pousada selecionada e a tua posição ficam por cima dos outros marcadores
            marcadores.sortedBy {
                when (it.estilo) {
                    EstiloMarcador.EU -> 2
                    EstiloMarcador.PRECO_SELECIONADO -> 1
                    else -> 0
                }
            }.forEach { m ->
                vista.overlays.add(Marker(vista).apply {
                    position = GeoPoint(m.latitude, m.longitude)
                    icon = iconeMarcador(vista.context, if (afastado && m.estilo == EstiloMarcador.PRECO) m.copy(estilo = EstiloMarcador.PONTO_POUSADA) else m)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                    setInfoWindow(null)
                    setOnMarkerClickListener { _, _ ->
                        onMarcador(m.id)
                        true
                    }
                })
            }
            vista.invalidate()
        }
    )
}

private fun criarMapa(context: Context): MapView {
    Configuration.getInstance().apply {
        load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
        // O OpenStreetMap pede que cada app se identifique (sem isto os mapas ficam cinzentos)
        userAgentValue = context.packageName
        // Cache dos mapas dentro da app (as versões novas do Android não deixam usar a pasta pública)
        osmdroidBasePath = File(context.cacheDir, "osmdroid")
        osmdroidTileCache = File(osmdroidBasePath, "tiles")
    }
    return MapView(context).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        setTilesScaledToDpi(true)
        setHorizontalMapRepetitionEnabled(false)
        setMinZoomLevel(4.0)
        controller.setZoom(6.5)
        controller.setCenter(GeoPoint(39.6, -8.0))
        onResume()
    }
}

private fun enquadrarPontos(mapa: MapView, pontos: List<GeoPoint>, zoomMaximo: Double) {
    when {
        pontos.isEmpty() -> return
        pontos.size == 1 -> mapa.controller.animateTo(pontos.first(), zoomMaximo, 600L)
        else -> {
            val margem = (48 * mapa.resources.displayMetrics.density).toInt()
            mapa.zoomToBoundingBox(BoundingBox.fromGeoPoints(pontos), true, margem)
        }
    }
}

/** Desenha o ícone de cada marcador: retângulo de preço, círculo de paragem, ponto de património... */
private fun iconeMarcador(context: Context, m: MarcadorMapa): Drawable {
    val d = context.resources.displayMetrics.density
    val bitmap = when (m.estilo) {
        EstiloMarcador.PRECO, EstiloMarcador.PRECO_SELECIONADO -> {
            val selecionado = m.estilo == EstiloMarcador.PRECO_SELECIONADO
            val texto = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                textSize = (if (selecionado) 15f else 13f) * d
                typeface = Typeface.DEFAULT_BOLD
                color = if (selecionado) TextoSobreClaro.toArgb() else android.graphics.Color.WHITE
            }
            val padH = 10 * d
            val padV = 6 * d
            val largura = (texto.measureText(m.texto) + 2 * padH).toInt()
            val altura = (texto.textSize + 2 * padV).toInt()
            val borda = 2 * d
            Bitmap.createBitmap(largura + (2 * borda).toInt(), altura + (2 * borda).toInt(), Bitmap.Config.ARGB_8888).also { b ->
                val c = Canvas(b)
                val fundo = Paint(Paint.ANTI_ALIAS_FLAG)
                fundo.color = android.graphics.Color.WHITE
                c.drawRect(0f, 0f, b.width.toFloat(), b.height.toFloat(), fundo)
                fundo.color = (if (selecionado) Amarelo else Azul).toArgb()
                c.drawRect(RectF(borda, borda, b.width - borda, b.height - borda), fundo)
                c.drawText(m.texto, borda + padH, b.height / 2f - (texto.descent() + texto.ascent()) / 2, texto)
            }
        }
        EstiloMarcador.PARAGEM, EstiloMarcador.NUMERO, EstiloMarcador.INICIO -> {
            val tamanho = ((if (m.estilo == EstiloMarcador.PARAGEM) 24 else 28) * d).toInt()
            Bitmap.createBitmap(tamanho, tamanho, Bitmap.Config.ARGB_8888).also { b ->
                val c = Canvas(b)
                val p = Paint(Paint.ANTI_ALIAS_FLAG)
                val r = tamanho / 2f
                p.color = android.graphics.Color.WHITE
                c.drawCircle(r, r, r, p)
                p.color = m.cor.toArgb()
                c.drawCircle(r, r, r - 2.5f * d, p)
                p.color = android.graphics.Color.WHITE
                p.textSize = 12 * d
                p.typeface = Typeface.DEFAULT_BOLD
                p.textAlign = Paint.Align.CENTER
                c.drawText(m.texto, r, r - (p.descent() + p.ascent()) / 2, p)
            }
        }
        EstiloMarcador.PONTO_POUSADA -> {
            val tamanho = (16 * d).toInt()
            Bitmap.createBitmap(tamanho, tamanho, Bitmap.Config.ARGB_8888).also { b ->
                val c = Canvas(b)
                val p = Paint(Paint.ANTI_ALIAS_FLAG)
                p.color = android.graphics.Color.WHITE
                c.drawRect(0f, 0f, tamanho.toFloat(), tamanho.toFloat(), p)
                p.color = Azul.toArgb()
                c.drawRect(2.5f * d, 2.5f * d, tamanho - 2.5f * d, tamanho - 2.5f * d, p)
            }
        }
        EstiloMarcador.EU -> {
            // Ponto azul com contorno branco e halo, como nas apps de mapas
            val tamanho = (34 * d).toInt()
            Bitmap.createBitmap(tamanho, tamanho, Bitmap.Config.ARGB_8888).also { b ->
                val c = Canvas(b)
                val p = Paint(Paint.ANTI_ALIAS_FLAG)
                val r = tamanho / 2f
                p.color = Azul.copy(alpha = 0.25f).toArgb()
                c.drawCircle(r, r, r, p)
                p.color = android.graphics.Color.WHITE
                c.drawCircle(r, r, 9 * d, p)
                p.color = Azul.toArgb()
                c.drawCircle(r, r, 6.5f * d, p)
            }
        }
        EstiloMarcador.PATRIMONIO -> {
            val tamanho = (18 * d).toInt()
            Bitmap.createBitmap(tamanho, tamanho, Bitmap.Config.ARGB_8888).also { b ->
                val c = Canvas(b)
                val p = Paint(Paint.ANTI_ALIAS_FLAG)
                val r = tamanho / 2f
                p.color = android.graphics.Color.WHITE
                c.drawCircle(r, r, r, p)
                p.color = Laranja.toArgb()
                c.drawCircle(r, r, r - 3 * d, p)
            }
        }
    }
    return BitmapDrawable(context.resources, bitmap)
}
