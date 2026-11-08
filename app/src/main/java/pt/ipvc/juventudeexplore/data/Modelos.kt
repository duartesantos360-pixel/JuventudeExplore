package pt.ipvc.juventudeexplore.data

import androidx.annotation.StringRes
import pt.ipvc.juventudeexplore.R

enum class TipoTransporte { A_PE, METRO, AUTOCARRO, ELETRICO, COMBOIO, BICICLETA }

// Os títulos são ids de textos (strings.xml)
enum class ModoTrajeto(@StringRes val titulo: Int, val googleMaps: String) {
    TRANSPORTES(R.string.modo_transportes, "transit"),
    A_PE(R.string.modo_a_pe, "walking"),
    BICICLETA(R.string.modo_bicicleta, "bicycling")
}

data class Paragem(
    val nome: String,
    val tipo: TipoTransporte,
    val latitude: Double,
    val longitude: Double
)

/** Preços por noite. null = esse tipo de quarto não existe nesta pousada. */
data class Precos(
    val dormitorio: Double,
    val duplo: Double?,
    val privado: Double?
) {
    val desde: Double get() = dormitorio
}

data class Acessibilidade(
    val entradaSemDegraus: Boolean,
    val elevador: Boolean,
    val quartoAdaptado: Boolean,
    val wcAdaptado: Boolean
) {
    val acessivel: Boolean get() = entradaSemDegraus && quartoAdaptado
}

/** Regiões pela ordem em que aparecem (de norte para sul). */
enum class Regiao(@StringRes val titulo: Int) {
    NORTE(R.string.regiao_norte),
    CENTRO(R.string.regiao_centro),
    LISBOA(R.string.regiao_lisboa),
    ALENTEJO(R.string.regiao_alentejo),
    ALGARVE(R.string.regiao_algarve)
}

data class Pousada(
    val id: Int,
    val nome: String,
    val cidade: String,
    val regiao: Regiao,
    val bairro: String,
    val morada: String,
    val descricao: String,
    val precos: Precos,
    /** null = ainda sem informação de acessibilidade. */
    val acessibilidade: Acessibilidade?,
    val latitude: Double,
    val longitude: Double,
    val paragens: List<Paragem>,
    /** Cor do espaço da fotografia (aparece enquanto a foto carrega). */
    val corFoto: Long,
    val fotoUrl: String? = null
) {
    val acessivel: Boolean get() = acessibilidade?.acessivel == true
}

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

enum class TipoQuarto(@StringRes val titulo: Int, @StringRes val descricao: Int) {
    DORMITORIO(R.string.quarto_dormitorio, R.string.quarto_dormitorio_desc),
    DUPLO(R.string.quarto_duplo, R.string.quarto_ate_2),
    PRIVADO(R.string.quarto_privado, R.string.quarto_ate_2)
}
