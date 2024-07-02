package com.example.prjfinalmobile.Forms

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun frmSobre(){
    Column (
        modifier = Modifier

    ){
        Text(text = "Desenvolvido por: \n" +
                "Rodrigo de Paula Pires Filho \n" +
                "19 anos"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSobre(){
    frmSobre()
}