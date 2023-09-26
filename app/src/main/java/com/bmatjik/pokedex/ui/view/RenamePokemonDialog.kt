package com.bmatjik.pokedex.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.bmatjik.pokedex.ui.theme.PokeDexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenamePokemonDialog(onClickRename:(String)->Unit={},onClickRelease:()->Unit={},openDialog: Boolean=true,pokemonName:String="") {
    var value by remember {
        mutableStateOf(pokemonName)
    }

    if (openDialog) {
        AlertDialog(onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
        }, dismissButton = {
            TextButton(onClick = onClickRelease) {
                Text(text = "Release")
            }
        }, confirmButton = {
            TextButton(onClick = { onClickRename(value) }) {
                Text(text = "Rename")
            }
        }, title = {
            Text(text = "Success Catch Pokemon")
        }, text = {
            Column {
                OutlinedTextField(value = value, onValueChange = {
                    value = it
                }, label = {
                    Text("Rename Pokemon")
                })
            }
        }, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ))
    }
}


@Composable
@Preview(device = Devices.PIXEL_2, showSystemUi = true, showBackground = true)
fun RenamePokemonDialogPreview() {
    PokeDexTheme {
        Column {
            RenamePokemonDialog()
        }
    }
}