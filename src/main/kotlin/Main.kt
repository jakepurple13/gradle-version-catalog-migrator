@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import component.CopyButton
import component.CustomTextField
import theme.Background
import theme.PurpleBlue
import util.APP_NAME
import util.CLOSE
import util.MINIMISE

@Composable
@Preview
fun App() {
    var inputDependencyText by remember { mutableStateOf("") }
    var outputDependencyText by remember { mutableStateOf("") }
    var outputDependencyTomlText by remember { mutableStateOf("") }
    var inputPluginText by remember { mutableStateOf("") }
    var outputPluginText by remember { mutableStateOf("") }
    var outputPluginTomlText by remember { mutableStateOf("") }
    var outputVersionsTomlText by remember { mutableStateOf("") }

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = Background,
            primary = PurpleBlue,
        )
    ) {
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(24.dp)
                    .verticalScroll(scrollState)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    CustomTextField(
                        text = inputDependencyText,
                        labelText = "Paste the dependencies here"
                    ) { inputDependencyText = it }

                    CustomTextField(
                        text = inputPluginText,
                        labelText = "Paste the plugins here"
                    ) { inputPluginText = it }
                }

                Button(
                    onClick = {
                        if (inputDependencyText.isNotBlank()) {
                            outputDependencyText = GradleCatalogUtils.convertDependencies(inputDependencyText.trim())
                            outputDependencyTomlText = GradleCatalogUtils.convertDependenciesToToml()
                        }
                        if (inputPluginText.isNotBlank()) {
                            outputPluginText = GradleCatalogUtils.convertPlugins(inputPluginText.trim())
                            outputPluginTomlText = GradleCatalogUtils.convertPluginsToToml()
                        }

                        outputVersionsTomlText = GradleCatalogUtils.getVersionsToml()
                    },
                    colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.DarkGray),
                    enabled = inputDependencyText.isNotBlank() || inputPluginText.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 190.dp)
                ) {
                    Text("Convert", fontWeight = FontWeight.Bold)
                }

                FlowRow(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(contentAlignment = Alignment.TopEnd) {
                        CustomTextField(
                            text = outputDependencyText,
                            labelText = "Find the updated dependencies here",
                            readOnly = true,
                            focusable = false
                        ) { outputDependencyText = it }

                        CopyButton(outputDependencyText)
                    }

                    Box(contentAlignment = Alignment.TopEnd) {
                        CustomTextField(
                            text = outputPluginText,
                            labelText = "Find the updated plugins here",
                            readOnly = true,
                            focusable = false
                        ) { outputPluginText = it }

                        CopyButton(outputPluginText)
                    }

                    Box(contentAlignment = Alignment.TopEnd) {
                        CustomTextField(
                            text = outputVersionsTomlText,
                            labelText = "Find the updated toml versions here",
                            readOnly = true,
                            focusable = false
                        ) { outputVersionsTomlText = it }

                        CopyButton(outputVersionsTomlText)
                    }

                    Box(contentAlignment = Alignment.TopEnd) {
                        CustomTextField(
                            outputDependencyTomlText,
                            "Find the updated toml dependencies here",
                            true,
                            focusable = false
                        ) { outputDependencyTomlText = it }

                        CopyButton(outputDependencyTomlText)
                    }

                    Box(modifier = Modifier, contentAlignment = Alignment.TopEnd) {
                        CustomTextField(
                            text = outputPluginTomlText,
                            labelText = "Find the updated toml plugins here",
                            readOnly = true,
                            focusable = false
                        ) { outputPluginTomlText = it }

                        CopyButton(outputPluginTomlText)
                    }
                }

                Button(
                    onClick = { GradleCatalogUtils.setupToml() },
                    colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.DarkGray),
                    enabled = outputDependencyText.isNotBlank() || outputPluginText.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 190.dp)
                ) {
                    Text("Create Toml", fontWeight = FontWeight.Bold)
                }
            }

            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState),
                style = LocalScrollbarStyle.current.copy(
                    hoverColor = Color.White
                ),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
            )
        }
    }
}

@Composable
private fun WindowScope.AppWindowTitleBar(state: WindowState, onClose: () -> Unit) = WindowDraggableArea {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Background)
            .padding(horizontal = 16.dp),
    ) {
        TopAppBar(
            title = {
                Text(
                    APP_NAME,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PurpleBlue,
                titleContentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 160.dp)
                .clip(RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)),
        )

        TopAppBar(
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
            actions = {
                IconButton(
                    onClick = { state.isMinimized = true }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = MINIMISE,
                        tint = Color.White,
                    )
                }

                IconButton(
                    onClick = onClose
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = CLOSE,
                        tint = Color.White,
                    )
                }
            }
        )
    }
}

fun main() = application {
    val windowState = WindowState(
        width = 850.dp,
        height = 740.dp,
        position = WindowPosition(Alignment.Center)
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = APP_NAME,
        state = windowState,
        resizable = true,
        undecorated = true,
        icon = painterResource("gradle.svg")
    ) {
        Column(Modifier.fillMaxSize()) {
            AppWindowTitleBar(windowState) { exitApplication() }
            App()
        }
    }
}
