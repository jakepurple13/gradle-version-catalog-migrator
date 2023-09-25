package component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.PurpleBlue
import theme.TextFieldBackground
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Composable
fun CustomTextField(
    text: String,
    labelText: String,
    readOnly: Boolean = false,
    focusable: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(labelText, fontStyle = FontStyle.Italic) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White.copy(alpha = 0.4f),
            focusedContainerColor = TextFieldBackground,
            unfocusedContainerColor = TextFieldBackground,
            disabledContainerColor = TextFieldBackground,
            focusedIndicatorColor = if (focusable) PurpleBlue else Color.Transparent,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White.copy(alpha = 0.4f),
            focusedPlaceholderColor = Color.White.copy(alpha = 0.4f)
        ),
        readOnly = readOnly,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(280.dp)
            .width(378.dp)
    )
}

@Composable
fun CopyButton(text: String) {
    ElevatedAssistChip(
        onClick = {
            val toolkit = Toolkit.getDefaultToolkit()
            val clipboard = toolkit.systemClipboard
            clipboard.setContents(StringSelection(text), null)
            toolkit.beep()
        },
        label = { Text("COPY", letterSpacing = (0.3).sp) },
        enabled = text.isNotBlank(),
        colors = AssistChipDefaults.elevatedAssistChipColors(
            disabledContainerColor = Color.DarkGray
        ),
        modifier = Modifier
            .padding(8.dp)
            .alpha(0.9f)
    )
}