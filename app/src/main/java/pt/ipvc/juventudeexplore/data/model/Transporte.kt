package pt.ipvc.juventudeexplore.data.model

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
