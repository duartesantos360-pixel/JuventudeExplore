# Juventude Explore — Ecrãs e fluxo de navegação

App Android (Kotlin + Jetpack Compose) de turismo jovem sustentável com as Pousadas de Juventude (Programação Móvel, ODS 11).

Esta versão tem **os ecrãs e a navegação** entre eles, com dados de exemplo fixos (6 pousadas e 6 roteiros).

## Abrir o projeto
1. Android Studio → File → Open → escolher esta pasta (`JuventudeExplore`).
2. Esperar pelo "Gradle sync" (a primeira vez demora, porque descarrega as bibliotecas).
3. Escolher um emulador (ou o telemóvel) e carregar em ▶ Run.

## Ecrãs
Barra de baixo com 5 separadores:
- **Explorar** — pesquisa, filtros e lista de pousadas
- **Mapa** — mapa do OpenStreetMap com o preço de cada pousada e os pontos de património
- **Roteiros** — roteiros a pé, filtrados por região
- **Reservas** — as minhas reservas
- **Perfil** — conta, aparência e pousadas favoritas

Ecrãs abertos a partir destes:
- **Ficha da pousada** — preços, acessibilidade, "Como chegar" e "Roteiro a pé"
- **Reservar** — tipo de quarto, dados e confirmação
- **Como chegar** — transportes / a pé / bicicleta e abrir no Google Maps
- **Roteiro a pé** — mapa do percurso e paragens
- **Entrar / criar conta**

## Fluxo de navegação
```
Abertura → Explorar ─┬─ Ficha da pousada ─┬─ Como chegar → Google Maps
                     │                    ├─ Roteiro a pé → Google Maps
                     │                    └─ Reservar → Reserva confirmada → Reservas
                     ├─ Reservar
                     └─ Ver no mapa → Mapa → Ficha da pousada / Reservar
Roteiros → Roteiro a pé
Reservas → Ficha da pousada / Explorar
Perfil → Entrar ou criar conta, Ficha da pousada, Reservar
```

## Estrutura
```
app/src/main/java/pt/ipvc/juventudeexplore/
├── MainActivity.kt                 abre a app (ecrã de abertura + navegação)
├── data/
│   ├── model/                      classes de dados
│   │   ├── Pousada.kt              Pousada, Precos, Acessibilidade, Regiao, TipoQuarto
│   │   ├── Roteiro.kt              Roteiro, PontoInteresse
│   │   └── Transporte.kt           Paragem, TipoTransporte, ModoTrajeto
│   └── DadosExemplo.kt             6 pousadas e 6 roteiros de exemplo (fixos)
├── ui/
│   ├── navigation/
│   │   ├── Rotas.kt                o endereço de cada ecrã
│   │   └── JuventudeExploreApp.kt  NavHost e barra de navegação de baixo
│   ├── screens/                    um ficheiro por ecrã, agrupados por funcionalidade
│   │   ├── abertura/               AberturaScreen
│   │   ├── explorar/               ExplorarScreen
│   │   ├── mapa/                   MapaScreen
│   │   ├── pousada/                PousadaDetalheScreen, ComoChegarScreen
│   │   ├── reservas/               ReservaScreen (+ confirmação), ReservasScreen
│   │   ├── roteiros/               RoteirosScreen, RoteiroDetalheScreen
│   │   └── perfil/                 PerfilScreen, ContaScreen
│   ├── components/                 peças usadas em vários ecrãs
│   │   ├── Botoes.kt               botões e filtros
│   │   ├── Textos.kt               títulos, etiquetas e caixa de preço
│   │   ├── Cartoes.kt              cabeçalho, cartão da pousada, caixas
│   │   ├── Imagens.kt              logótipo, triângulo, fotos, linhas do fundo
│   │   ├── Transportes.kt          cor e letra de cada transporte
│   │   └── MapaOsm.kt              mapa do OpenStreetMap (osmdroid)
│   └── theme/
│       ├── Cores.kt                cores do site pousadasjuventude.pt
│       ├── Tipografia.kt           letra Unbounded e tamanhos de texto
│       └── Theme.kt                JuventudeExploreTheme (modo claro e escuro)
└── util/
    ├── Formatacao.kt               preços ("18,00€") e distâncias ("2,4 km")
    ├── Distancia.kt                minutos a pé entre dois pontos
    └── GoogleMaps.kt               abrir trajetos no Google Maps
```

## Pré-visualização dos ecrãs
Cada ecrã tem um `@Preview` no fim do ficheiro. Abrir o ficheiro e carregar em **Split** ou **Design** (canto superior direito) para ver o ecrã sem correr a app.

## Trabalho em grupo (Git)
- Cada tarefa num **branch** próprio (`git checkout -b nome-da-tarefa`), com commits pequenos.
- Juntar ao `main` por **pull request** no GitHub.
- No fim de cada checkpoint marca-se a versão com uma tag (`cp1`, `cp2`, ...).
