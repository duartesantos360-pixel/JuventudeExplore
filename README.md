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
- `ui/navigation/JuventudeExploreApp.kt` — NavHost, rotas e barra de navegação de baixo
- `ui/screens/` — um ficheiro por ecrã; `Componentes.kt` tem os botões, cartões e títulos usados em todos
- `ui/theme/` — cores e letra (tiradas do site pousadasjuventude.pt)
- `data/` — modelos (`Pousada`, `Roteiro`...) e dados de exemplo
