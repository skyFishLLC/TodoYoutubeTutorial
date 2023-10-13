package dev.skyfish.todo.feature_todo.presentation.todo_new_update.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun HintTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    fontSize: TextUnit = 32.sp,
    textColor: Color,
    isHintVisible: Boolean = true,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit
){
    Box(
        modifier = modifier
    ){
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = TextStyle(
                fontSize = fontSize,
                color = textColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )
        if(isHintVisible){
            Text(
                text = hint,
                color = textColor,
                fontSize = fontSize
            )
        }
    }
}












