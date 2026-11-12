package pt.ipvc.juventudeexplore.data.model

import androidx.annotation.StringRes
import pt.ipvc.juventudeexplore.R

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

enum class TipoQuarto(@StringRes val titulo: Int, @StringRes val descricao: Int) {
    DORMITORIO(R.string.quarto_dormitorio, R.string.quarto_dormitorio_desc),
    DUPLO(R.string.quarto_duplo, R.string.quarto_ate_2),
    PRIVADO(R.string.quarto_privado, R.string.quarto_ate_2)
}
