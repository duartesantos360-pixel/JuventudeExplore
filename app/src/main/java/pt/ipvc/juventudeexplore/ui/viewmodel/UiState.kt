package pt.ipvc.juventudeexplore.ui.viewmodel
import androidx.annotation.StringRes
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.data.model.Regiao
import pt.ipvc.juventudeexplore.data.model.Roteiro
// Filtros por cima da lista de pousadas (Explorar)
enum class Filtro(@StringRes val titulo: Int) {
    TODAS(R.string.filtro_todas),
    MAIS_BARATAS(R.string.filtro_baratas),
    ACESSIVEIS(R.string.filtro_acessiveis)
}
// Tudo o que os ecras mostram, num so objeto.
// Os ecras so leem isto; quem o muda e o ViewModel.
data class UiState(
// texto escrito na pesquisa
    val pesquisa: String = "",
// filtro escolhido no Explorar
    val filtro: Filtro = Filtro.TODAS,
// regiao escolhida nos Roteiros (null = todas)
    val regiao: Regiao? = null,
// pousadas ja filtradas
    val pousadas: List<Pousada> = emptyList(),
// roteiros ja filtrados
    val roteiros: List<Roteiro> = emptyList(),
// regioes que tem roteiros (para os botoes)
    val regioes: List<Regiao> = emptyList()
)
