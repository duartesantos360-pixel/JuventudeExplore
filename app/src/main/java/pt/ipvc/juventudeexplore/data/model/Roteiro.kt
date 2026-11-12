package pt.ipvc.juventudeexplore.data.model

data class PontoInteresse(
    val nome: String,
    val descricao: String,
    /** Minutos a pé desde a paragem anterior. */
    val minutos: Int,
    val latitude: Double,
    val longitude: Double
)

data class Roteiro(
    val id: Int,
    val nome: String,
    val cidade: String,
    val pousadaId: Int,
    val distanciaKm: Double,
    val pontos: List<PontoInteresse>
) {
    val duracaoMinutos: Int get() = pontos.sumOf { it.minutos }
}
