package pt.ipvc.juventudeexplore.data.repository

import pt.ipvc.juventudeexplore.data.DadosExemplo
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.data.model.Roteiro

// Unica porta de entrada para os dados da app.
// Por agora usa os dados de exemplo; no CP3 passa a usar a API.
class JuventudeRepositorio {

    // Todas as pousadas
    fun pousadas(): List<Pousada> = DadosExemplo.pousadas

    // Uma pousada pelo numero (id), ou null se nao existir
    fun pousada(id: Int): Pousada? = DadosExemplo.pousada(id)

    // Todos os roteiros a pe
    fun roteiros(): List<Roteiro> = DadosExemplo.roteiros

    // Um roteiro pelo numero (id), ou null se nao existir
    fun roteiro(id: Int): Roteiro? = DadosExemplo.roteiro(id)

    // O roteiro que comeca numa pousada
    fun roteiroDaPousada(pousadaId: Int): Roteiro? = DadosExemplo.roteiroDaPousada(pousadaId)
}