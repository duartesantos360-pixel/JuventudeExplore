package pt.ipvc.juventudeexplore.ui.viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pt.ipvc.juventudeexplore.data.model.Regiao
import pt.ipvc.juventudeexplore.data.repository.JuventudeRepositorio
// Guarda o estado da app e a logica que antes estava dentro dos ecras.
class JuventudeExploreViewModel : ViewModel() {
    // De onde vem os dados (feito pelo Mota)
    private val repositorio = JuventudeRepositorio()
    // _uiState: so o ViewModel pode mudar
    private val _uiState = MutableStateFlow(UiState())
    // uiState: os ecras so podem ler
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    // Quando o ViewModel e criado, preenche as listas
    init {
        atualizarListas()
    }
    // Chamado quando se escreve na pesquisa
    fun mudarPesquisa(texto: String) {
        _uiState.update { it.copy(pesquisa = texto) }
        atualizarListas()
    }
    // Chamado quando se escolhe Todas / Mais baratas / Acessiveis
    fun mudarFiltro(filtro: Filtro) {
        _uiState.update { it.copy(filtro = filtro) }
        atualizarListas()
    }
    // Chamado quando se escolhe uma regiao nos Roteiros (null = todas)
    fun mudarRegiao(regiao: Regiao?) {
        _uiState.update { it.copy(regiao = regiao) }
        atualizarListas()
    }
    // Volta a calcular as listas com a pesquisa e os filtros atuais
    private fun atualizarListas() {
        val estado = _uiState.value
        val texto = estado.pesquisa.trim()
// 1. Pousadas: primeiro a pesquisa (cidade ou nome)...
        val encontradas = repositorio.pousadas().filter {
            texto.isBlank() ||
                    it.cidade.contains(texto, ignoreCase = true) ||
                    it.nome.contains(texto, ignoreCase = true)
        }
// ...depois o filtro escolhido
        val pousadas = when (estado.filtro) {
            Filtro.TODAS -> encontradas
            Filtro.MAIS_BARATAS -> encontradas.sortedBy { it.precos.desde }
            Filtro.ACESSIVEIS -> encontradas.filter { it.acessivel }
        }
// 2. Roteiros: a regiao de um roteiro e a regiao da pousada onde comeca
        val todosRoteiros = repositorio.roteiros()
        val regiaoDoRoteiro = { pousadaId: Int -> repositorio.pousada(pousadaId)?.regiao }
        val regioes = Regiao.entries.filter { r ->
            todosRoteiros.any { regiaoDoRoteiro(it.pousadaId) == r }
        }
        val roteiros = todosRoteiros.filter {
            estado.regiao == null || regiaoDoRoteiro(it.pousadaId) == estado.regiao
        }
// 3. Guarda tudo no estado (os ecras atualizam-se sozinhos)
        _uiState.update {
            it.copy(pousadas = pousadas, roteiros = roteiros, regioes = regioes)
        }
    }
}