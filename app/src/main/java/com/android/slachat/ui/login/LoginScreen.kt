package com.android.slachat.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.slachat.R
import com.android.slachat.data.SignInModel
import com.android.slachat.ui.theme.EduSABeginnerFont
import com.android.slachat.viewmodel.LoginViewModel
import org.koin.androidx.compose.get

val bodyMedium = TextStyle(
    fontFamily = EduSABeginnerFont,
    fontSize = 50.sp,
    fontWeight = FontWeight.Normal,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val signInPresentation = get<LoginViewModel>()

    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = colorResource(id = R.color.silver),
        unfocusedBorderColor = colorResource(id = R.color.silver),
        containerColor = colorResource(id = R.color.light_silver),
        cursorColor = colorResource(id = R.color.silver),
        placeholderColor = colorResource(id = R.color.silver),
        disabledPlaceholderColor = colorResource(id = R.color.silver),
        focusedLabelColor = colorResource(id = R.color.silver),
        unfocusedLabelColor = colorResource(id = R.color.silver)
    )

    var passwordField by remember { mutableStateOf(TextFieldValue("Horosh_200271")) }
    var loginField by remember { mutableStateOf(TextFieldValue("Slavuha")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_view),
                    end = dimensionResource(id = R.dimen.padding_view),
                    top = dimensionResource(id = R.dimen.padding_view),
                )
                .fillMaxHeight(), // Fill the height of the available space
            verticalArrangement = Arrangement.Center
        ) {
            LoginContent(
                customTextFieldColors = customTextFieldColors,
                loginField = loginField,
                onLoginFieldChange = { newValue -> loginField = newValue },
                passwordField = passwordField,
                onPasswordFieldChange = { newValue -> passwordField = newValue },
                onLoginButtonClick = {
                    signInPresentation.signIn(
                        SignInModel(loginField.text, passwordField.text),
                        navController
                    )
                },
                onForgotPasswordClick = {

                }
            )
        }

        // Place the ErrorSnackBar at the bottom of the screen
        ErrorSnackBar(signInPresentation, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ErrorSnackBar(viewModel: LoginViewModel, modifier: Modifier = Modifier) {
    val errorState by viewModel.errorEvent.collectAsState(initial = "")

    if (errorState.isNotEmpty()) {
        Snackbar(
            modifier = modifier.padding(10.dp),
            content = {
                Text(text = errorState)
            },
            action = {
                TextButton(
                    onClick = { viewModel.hideSnackBar() },
                ) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}


@Composable
fun LoginContent(
    customTextFieldColors: TextFieldColors,
    loginField: TextFieldValue,
    onLoginFieldChange: (TextFieldValue) -> Unit,
    passwordField: TextFieldValue,
    onPasswordFieldChange: (TextFieldValue) -> Unit,
    onLoginButtonClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    TitleText()
    DescriptionText()
    LoginField(
        customTextFieldColors = customTextFieldColors,
        loginField = loginField,
        onLoginFieldChange = onLoginFieldChange
    )

    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_view)))
    PasswordField(
        customTextFieldColors = customTextFieldColors,
        passwordField = passwordField,
        onPasswordFieldChange = onPasswordFieldChange
    )

    LoginButton(callback = onLoginButtonClick)
    ForgotPassword(onClick = onForgotPasswordClick)
}


@Composable
fun TitleText() {
    Text(
        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_view)),
        text = stringResource(id = R.string.app_title),
        style = bodyMedium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    customTextFieldColors: TextFieldColors,
    loginField: TextFieldValue,
    onLoginFieldChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = loginField,
        colors = customTextFieldColors,
        onValueChange = { newValue ->
            onLoginFieldChange(newValue)
        },
        singleLine = true,
        label = { Text(text = stringResource(R.string.enter_your_name)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    customTextFieldColors: TextFieldColors,
    passwordField: TextFieldValue = TextFieldValue("Horosh_200271"),
    onPasswordFieldChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = passwordField,
        colors = customTextFieldColors,
        onValueChange = { newValue ->
            onPasswordFieldChange(newValue)
        },
        singleLine = true,
        label = { Text(text = stringResource(R.string.enter_password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
    )
}


@Composable
fun DescriptionText() {
    Text(
        text = stringResource(id = R.string.app_description),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_small),
            start = dimensionResource(id = R.dimen.padding_view),
            bottom = dimensionResource(R.dimen.padding_small)
        )
    )
}

@Composable
fun LoginButton(callback: () -> Unit) {
    Button(
        onClick = { callback.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.medium_padding)
            )
            .height(dimensionResource(id = R.dimen.login_button_size)),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.login_button))
    ) {
        Text(
            text = stringResource(id = R.string.sign_in),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ForgotPassword(onClick: () -> Unit) {
    Text(
        text = stringResource(R.string.forgot_password),
        color = colorResource(id = R.color.black),
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(
                top = dimensionResource(id = R.dimen.padding_view),
                start = dimensionResource(id = R.dimen.padding_small)
            ),
        style = MaterialTheme.typography.bodyLarge,
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}
