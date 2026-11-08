package pt.ipvc.juventudeexplore.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import pt.ipvc.juventudeexplore.R
import pt.ipvc.juventudeexplore.ui.theme.Azul
import pt.ipvc.juventudeexplore.ui.theme.Branco
import pt.ipvc.juventudeexplore.ui.theme.Cinzento

/** Entrar / criar conta (só o desenho: o botão volta ao Perfil). */
@Composable
fun ContaScreen(onVoltar: () -> Unit) {
    var criar by rememberSaveable { mutableStateOf(false) }
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var palavraPasse by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Cabecalho(
            stringResource(if (criar) R.string.conta_criar else R.string.conta_entrar),
            onVoltar,
            subtitulo = stringResource(R.string.conta_subtitulo)
        )
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChipFiltro(stringResource(R.string.conta_ja_tenho), selecionado = !criar, onClick = { criar = false })
                ChipFiltro(stringResource(R.string.conta_criar), selecionado = criar, onClick = { criar = true })
            }

            if (criar) CampoConta(nome, { nome = it }, stringResource(R.string.conta_nome))
            CampoConta(email, { email = it }, stringResource(R.string.res_email), KeyboardType.Email)
            CampoConta(
                palavraPasse,
                { palavraPasse = it },
                stringResource(R.string.conta_palavra_passe),
                KeyboardType.Password,
                esconder = true
            )

            BotaoAzul(
                stringResource(if (criar) R.string.conta_criar else R.string.conta_entrar),
                onClick = onVoltar,
                modifier = Modifier.fillMaxWidth()
            )
            Text(stringResource(R.string.conta_explica), style = MaterialTheme.typography.bodySmall, color = Cinzento)
        }
    }
}

@Composable
private fun CampoConta(
    valor: String,
    onValor: (String) -> Unit,
    rotulo: String,
    teclado: KeyboardType = KeyboardType.Text,
    esconder: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValor,
        label = { Text(rotulo) },
        singleLine = true,
        visualTransformation = if (esconder) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        shape = RectangleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Branco,
            unfocusedContainerColor = Branco,
            focusedBorderColor = Azul
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
