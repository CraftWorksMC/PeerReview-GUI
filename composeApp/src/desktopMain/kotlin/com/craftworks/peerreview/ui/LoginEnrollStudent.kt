package com.craftworks.peerreview.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.craftworks.peerreview.login.LoginManager
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun EnrollStudentScreen() {
    var nameSurname: String by remember { mutableStateOf("") }
    var registerNumber: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Baobab.school Logo
        CoilImage(
            imageModel = { "src/commonMain/composeResources/drawable/BaobabLogoNew.png" },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center
            ),
            modifier = Modifier.width(320.dp).wrapContentHeight().padding(top = 16.dp)
        )

        // Email TextField
        OutlinedTextField(
            value = nameSurname,
            onValueChange = { nameSurname = it },
            label = { Text("Nome Cognome (Come nel registro)") },
            modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp)
        )

        // Password TextField
        OutlinedTextField(
            value = registerNumber,
            onValueChange = { registerNumber = it },
            label = { Text("Numero del registro") },
            modifier = Modifier
                .width(320.dp)
                .padding(vertical = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Login Button
        Button(
            onClick = {
                if (registerNumber.toInt() > 0)
                    LoginManager.isStudentEnrolled.value = true
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(320.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iscriviti")
        }
    }
}