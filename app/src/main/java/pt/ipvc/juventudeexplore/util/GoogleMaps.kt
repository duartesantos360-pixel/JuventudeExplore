package pt.ipvc.juventudeexplore.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun abrirTrajeto(context: Context, latitude: Double, longitude: Double, modo: String) {
    val uri = Uri.parse(
        "https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude&travelmode=$modo"
    )
    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
}

/** Abre o roteiro a pé no Google Maps: da pousada ao último ponto, passando pelos outros. */
fun abrirRoteiroNoMapa(context: Context, origem: Pair<Double, Double>, pontos: List<Pair<Double, Double>>) {
    if (pontos.isEmpty()) return
    val destino = pontos.last()
    val intermedios = pontos.dropLast(1).joinToString("|") { "${it.first},${it.second}" }
    val link = "https://www.google.com/maps/dir/?api=1" +
        "&origin=${origem.first},${origem.second}" +
        "&destination=${destino.first},${destino.second}" +
        (if (intermedios.isNotEmpty()) "&waypoints=${Uri.encode(intermedios)}" else "") +
        "&travelmode=walking"
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}
