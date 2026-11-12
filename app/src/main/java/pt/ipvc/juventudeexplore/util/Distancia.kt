package pt.ipvc.juventudeexplore.util

import kotlin.math.roundToInt

/** Tempo aproximado a pé entre dois pontos (linha reta x 1,3, a 5 km/h). */
fun minutosAPe(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
    val r = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val h = kotlin.math.sin(dLat / 2).let { it * it } +
        kotlin.math.cos(Math.toRadians(lat1)) * kotlin.math.cos(Math.toRadians(lat2)) *
        kotlin.math.sin(dLon / 2).let { it * it }
    val km = 2 * r * kotlin.math.asin(kotlin.math.sqrt(h))
    return (km * 1.3 / 5.0 * 60).roundToInt().coerceAtLeast(1)
}
