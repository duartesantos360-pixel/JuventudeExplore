package pt.ipvc.juventudeexplore.data

import pt.ipvc.juventudeexplore.data.model.Acessibilidade
import pt.ipvc.juventudeexplore.data.model.Paragem
import pt.ipvc.juventudeexplore.data.model.PontoInteresse
import pt.ipvc.juventudeexplore.data.model.Pousada
import pt.ipvc.juventudeexplore.data.model.Precos
import pt.ipvc.juventudeexplore.data.model.Regiao
import pt.ipvc.juventudeexplore.data.model.Roteiro
import pt.ipvc.juventudeexplore.data.model.TipoTransporte
import kotlin.math.roundToInt

/**
 * Dados de exemplo (fixos) para mostrar os ecrãs e a navegação.
 * Moradas, coordenadas e preço "desde" tirados do site pousadasjuventude.pt.
 * Mais tarde estes dados passam a vir de uma base de dados / web service.
 */
object DadosExemplo {

    private const val FOTOS = "https://pousadasjuventude.e-gds.com/webfrontendcorporate/images"

    /** Duplo e privado aproximados a partir do preço do dormitório. */
    private fun precos(dormitorio: Double) = Precos(
        dormitorio = dormitorio,
        duplo = (dormitorio * 2.4).roundToInt().toDouble(),
        privado = (dormitorio * 2.9).roundToInt().toDouble()
    )

    val pousadas = listOf(
        Pousada(
            id = 38,
            nome = "HI Viana do Castelo",
            cidade = "Viana do Castelo",
            regiao = Regiao.NORTE,
            bairro = "Viana do Castelo",
            morada = "Rua de Limia, Viana do Castelo",
            descricao = "Pousada virada para o rio Lima, junto à marina e a cerca de 1,5 km do centro.",
            precos = precos(23.0),
            acessibilidade = Acessibilidade(true, true, true, true),
            latitude = 41.69591, longitude = -8.81819,
            paragens = listOf(
                Paragem("Viana do Castelo (estação)", TipoTransporte.COMBOIO, 41.6946, -8.8305)
            ),
            corFoto = 0xFFD6F3EF,
            fotoUrl = "$FOTOS/80/capa%20(1).jpg"
        ),
        Pousada(
            id = 8,
            nome = "HI Braga",
            cidade = "Braga",
            regiao = Regiao.NORTE,
            bairro = "Braga",
            morada = "Rua de Santa Margarida, 6, Braga",
            descricao = "Pousada integrada no Centro de Juventude de Braga, a cerca de 10 minutos a pé do centro histórico e da Sé.",
            precos = precos(14.0),
            acessibilidade = Acessibilidade(true, false, true, true),
            latitude = 41.55397, longitude = -8.4171,
            paragens = listOf(
                Paragem("Braga (estação)", TipoTransporte.COMBOIO, 41.5481, -8.4345)
            ),
            corFoto = 0xFFCFEEF5,
            fotoUrl = "$FOTOS/88/capa.jpg"
        ),
        Pousada(
            id = 30,
            nome = "HI Porto",
            cidade = "Porto",
            regiao = Regiao.NORTE,
            bairro = "Porto",
            morada = "Rua Paulo da Gama, 551, Porto",
            descricao = "Pousada na zona ocidental do Porto, entre o Parque da Cidade e a foz do Douro.",
            precos = precos(21.0),
            acessibilidade = Acessibilidade(true, true, true, true),
            latitude = 41.14816, longitude = -8.65906,
            paragens = emptyList(),
            corFoto = 0xFFD6F3EF,
            fotoUrl = "$FOTOS/58/porto-1.jpg"
        ),
        Pousada(
            id = 11,
            nome = "HI Coimbra",
            cidade = "Coimbra",
            regiao = Regiao.CENTRO,
            bairro = "Coimbra",
            morada = "Rua Dr. Henriques Seco, 14, 3000-145 Coimbra",
            descricao = "Pousada perto do Jardim da Sereia, a cerca de 10 minutos a pé da Universidade de Coimbra.",
            precos = precos(26.0),
            acessibilidade = Acessibilidade(false, false, false, false),
            latitude = 40.21122, longitude = -8.41405,
            paragens = listOf(
                Paragem("Coimbra (Coimbra-A) (estação)", TipoTransporte.COMBOIO, 40.2089, -8.4336)
            ),
            corFoto = 0xFFFFEBB8,
            fotoUrl = "$FOTOS/67/coimbra-pousada.jpg"
        ),
        Pousada(
            id = 20,
            nome = "HI Lisboa",
            cidade = "Lisboa",
            regiao = Regiao.LISBOA,
            bairro = "Lisboa",
            morada = "Rua Andrade Corvo, 46, 1050-009 Lisboa",
            descricao = "Pousada no centro de Lisboa, a poucos metros do metro de Picoas e perto do Marquês de Pombal.",
            precos = precos(24.0),
            acessibilidade = Acessibilidade(true, true, true, false),
            latitude = 38.73051, longitude = -9.14784,
            paragens = listOf(
                Paragem("Picoas (metro)", TipoTransporte.METRO, 38.7311, -9.1463),
                Paragem("Marquês de Pombal (metro)", TipoTransporte.METRO, 38.7253, -9.15)
            ),
            corFoto = 0xFFCFEEF5,
            fotoUrl = "$FOTOS/53/capa%20(1).jpg"
        ),
        Pousada(
            id = 14,
            nome = "HI Faro",
            cidade = "Faro",
            regiao = Regiao.ALGARVE,
            bairro = "Faro",
            morada = "Rua da PSP, Edifício do IPDJ, 8000-408 Faro",
            descricao = "Pousada junto ao Jardim da Alameda, a cerca de 10 minutos a pé do centro e da Cidade Velha de Faro.",
            precos = precos(22.0),
            acessibilidade = null,
            latitude = 37.01455, longitude = -7.92659,
            paragens = listOf(
                Paragem("Faro (estação)", TipoTransporte.COMBOIO, 37.019, -7.9406)
            ),
            corFoto = 0xFFD6F3EF,
            fotoUrl = "$FOTOS/83/capa%20(1).jpg"
        )
    )

    val roteiros = listOf(
        Roteiro(
            id = 5,
            nome = "Centro histórico de Viana",
            cidade = "Viana do Castelo",
            pousadaId = 38,
            distanciaKm = 3.1,
            pontos = listOf(
                PontoInteresse("Navio Gil Eannes", "Antigo navio-hospital, hoje museu", 17, 41.6915, -8.8296),
                PontoInteresse("Praça da República", "Coração do centro histórico", 5, 41.6936, -8.8324),
                PontoInteresse("Santuário de Santa Luzia", "Subida de funicular com vista sobre o Lima", 15, 41.7024, -8.8339)
            )
        ),
        Roteiro(
            id = 3,
            nome = "Braga histórica",
            cidade = "Braga",
            pousadaId = 8,
            distanciaKm = 1.6,
            pontos = listOf(
                PontoInteresse("Jardim de Santa Bárbara", "Jardim junto ao Paço Episcopal", 13, 41.5518, -8.4266),
                PontoInteresse("Sé de Braga", "A catedral mais antiga de Portugal", 3, 41.5499, -8.42736),
                PontoInteresse("Arco da Porta Nova", "Antiga entrada da cidade", 3, 41.5506, -8.4292)
            )
        ),
        Roteiro(
            id = 1,
            nome = "Do Palácio de Cristal à Ribeira",
            cidade = "Porto",
            pousadaId = 30,
            distanciaKm = 5.5,
            pontos = listOf(
                PontoInteresse("Jardins do Palácio de Cristal", "Miradouro sobre o Douro", 44, 41.1484, -8.6253),
                PontoInteresse("Miragaia", "Bairro ribeirinho fora da antiga muralha", 12, 41.143, -8.6198),
                PontoInteresse("Ribeira", "Centro Histórico (Património Mundial)", 10, 41.1405, -8.613)
            )
        ),
        Roteiro(
            id = 4,
            nome = "Da Universidade à Baixa",
            cidade = "Coimbra",
            pousadaId = 11,
            distanciaKm = 1.9,
            pontos = listOf(
                PontoInteresse("Universidade de Coimbra", "Paço das Escolas e Biblioteca Joanina (Património Mundial)", 17, 40.2073, -8.4259),
                PontoInteresse("Sé Velha", "Catedral românica do século XII", 3, 40.2087, -8.4278),
                PontoInteresse("Arco de Almedina", "Porta da antiga muralha", 2, 40.2096, -8.4291)
            )
        ),
        Roteiro(
            id = 2,
            nome = "Das Avenidas Novas à Baixa",
            cidade = "Lisboa",
            pousadaId = 20,
            distanciaKm = 3.6,
            pontos = listOf(
                PontoInteresse("Avenida da Liberdade", "A grande avenida de Lisboa", 27, 38.7155, -9.1414),
                PontoInteresse("Rossio", "A praça central da Baixa Pombalina", 4, 38.71389, -9.13944),
                PontoInteresse("Praça do Comércio", "Junto ao Tejo, antiga praça do palácio real", 12, 38.7075, -9.13639)
            )
        ),
        Roteiro(
            id = 18,
            nome = "Faro, da Capela dos Ossos à Sé",
            cidade = "Faro",
            pousadaId = 14,
            distanciaKm = 2.1,
            pontos = listOf(
                PontoInteresse("Capela dos Ossos", "Capela forrada a ossos e caveiras", 14, 37.0199, -7.9345),
                PontoInteresse("Arco da Vila", "Porta de entrada na cidade velha", 9, 37.0146, -7.9349),
                PontoInteresse("Sé de Faro", "Catedral da cidade velha", 2, 37.0133, -7.9351)
            )
        )
    )

    fun pousada(id: Int): Pousada? = pousadas.find { it.id == id }

    fun roteiro(id: Int): Roteiro? = roteiros.find { it.id == id }

    fun roteiroDaPousada(pousadaId: Int): Roteiro? = roteiros.find { it.pousadaId == pousadaId }
}
