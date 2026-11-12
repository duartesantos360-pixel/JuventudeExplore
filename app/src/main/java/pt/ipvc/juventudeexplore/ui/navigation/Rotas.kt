package pt.ipvc.juventudeexplore.ui.navigation

/** Rotas (endereços) de cada ecrã. As que têm {id} recebem o id da pousada ou do roteiro. */
object Rotas {
    const val EXPLORAR = "explorar"
    const val MAPA = "mapa"
    const val ROTEIROS = "roteiros"
    const val RESERVAS = "reservas"
    const val PERFIL = "perfil"
    const val CONTA = "conta"
    const val POUSADA = "pousada/{id}"
    const val COMO_CHEGAR = "comochegar/{id}"
    const val ROTEIRO = "roteiro/{id}"
    const val RESERVA = "reserva/{id}"

    fun pousada(id: Int) = "pousada/$id"
    fun comoChegar(id: Int) = "comochegar/$id"
    fun roteiro(id: Int) = "roteiro/$id"
    fun reserva(id: Int) = "reserva/$id"
}
