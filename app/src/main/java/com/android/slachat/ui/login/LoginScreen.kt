package com.android.slachat.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.android.slachat.R
import com.android.slachat.presentation.SignInPresentation
import com.android.slachat.ui.theme.EduSABeginnerFont
import org.koin.androidx.compose.get

val bodyMedium = TextStyle(
    fontFamily = EduSABeginnerFont,
    fontSize = 50.sp,
    fontWeight = FontWeight.Normal,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val signInPresentation = get<SignInPresentation>()

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

    var passwordField by remember { mutableStateOf(TextFieldValue("")) }
    var loginField by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.padding_view),
                end = dimensionResource(id = R.dimen.padding_view),
            ),
        verticalArrangement = Arrangement.Center
    ) {
        LoginContent(
            customTextFieldColors = customTextFieldColors,
            loginField = loginField,
            onLoginFieldChange = { newValue -> loginField = newValue },
            passwordField = passwordField,
            onPasswordFieldChange = { newValue -> passwordField = newValue },
            onLoginButtonClick = {
                if (signInPresentation.checkFields(loginField.text, passwordField.text))
                    signInPresentation.signIn()
            },
            onForgotPasswordClick = { /* Handle Forgot Password click */ }
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
        label = { Text(text = stringResource(R.string.enter_your_name)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    customTextFieldColors: TextFieldColors,
    passwordField: TextFieldValue,
    onPasswordFieldChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = passwordField,
        colors = customTextFieldColors,
        onValueChange = { newValue ->
            onPasswordFieldChange(newValue)
        },
        label = { Text(text = stringResource(R.string.enter_password)) }
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
                top = dimensionResource(id = R.dimen.big_button_padding)
            )
            .height(dimensionResource(id = R.dimen.login_button_size)), // Use IntrinsicSize.Min to ensure the button's height is not cut off
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
        LoginScreen()
    }
}
