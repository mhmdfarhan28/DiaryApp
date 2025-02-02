package com.example.diaryapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    dialogOpened: Boolean,
    onCloseDialog: () -> Unit,
    onYesClicked: () -> Unit,
) {
   if(dialogOpened) {
       AlertDialog(
           title = {
               Text(
                   text = title,
                   fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                   fontWeight = FontWeight.Bold
               )
           },
           text = {
               Text(
                   text = message,
                   fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                   fontWeight = FontWeight.Normal
               )
           },
           onDismissRequest = onCloseDialog,
           confirmButton = {
               Button(
                   onClick = {
                       onYesClicked()
                       onCloseDialog()
                   }
               ) {
                   Text(text = "Yes")
               }
           },
           dismissButton = {
               OutlinedButton(onClick = onCloseDialog) {
                   Text(text = "No")
               }
           }
       )
   }
}