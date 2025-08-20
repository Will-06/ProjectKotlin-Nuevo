import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.R
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ChatMessage(val text: String, val isUser: Boolean, val time: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hola, ¿me puedes recomendar rutas?", true, "10:00"),
            ChatMessage("¡Hola! Claro que sí. ¿Cómo estás?", false, "10:01"),
            ChatMessage("¿Te puedo hacer una pregunta para ayudarte mejor?", false, "10:02"),
            ChatMessage("Sí, claro.", true, "10:03"),
            ChatMessage("Perfecto. ¿Qué tipo de rutas de ciclismo te interesan? ¿Montaña, carretera, algo fácil o más desafiante?", false, "10:04"),
            ChatMessage("Estoy buscando rutas para ciclismo de montaña cerca de mí.", true, "10:05"),
            ChatMessage("¡Genial! Tengo varias opciones cerca de tu ubicación. ¿Quieres que te cuente sobre la dificultad y la distancia de cada ruta?", false, "10:06"),
            ChatMessage("Sí, por favor. También me interesa saber si hay subidas exigentes.", true, "10:07"),
            ChatMessage("Por supuesto. La ruta 'Cerro Verde Trail' es de dificultad alta, con varias pendientes pronunciadas y 18 km en total.", false, "10:08"),
            ChatMessage("¿Hay algún taller cercano en caso de que tenga un problema con la bici?", true, "10:09"),
            ChatMessage("Sí, el taller 'BikeFix' está a 2 km de la ruta, abre de 8am a 6pm y tiene buenas reseñas.", false, "10:10"),
            ChatMessage("¡Perfecto! ¿Qué me recomiendas comer antes de iniciar la ruta?", true, "10:11"),
            ChatMessage("Lo mejor es algo ligero y energético, como avena con plátano o pan integral con miel 🍯.", false, "10:12"),
            ChatMessage("¿Puedo compartir mis rutas con otros ciclistas?", true, "10:13"),
            ChatMessage("Sí, en tu perfil puedes compartir rutas, comentar y ver las publicaciones de otros ciclistas 🧭.", false, "10:14"),
            ChatMessage("Gracias, ha sido muy útil.", true, "10:15"),
            ChatMessage("¡De nada! Estoy aquí para ayudarte cuando lo necesites. ¡Disfruta tu ruta! 🚴‍♀️", false, "10:16")
        )
    }

    var inputText by remember { mutableStateOf(TextFieldValue()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val bgColor = MaterialTheme.colorScheme.background
    val bubbleColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    val userBubbleColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
    val textColor = MaterialTheme.colorScheme.onBackground

    Scaffold(
        containerColor = bgColor,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(WindowInsets.ime.asPaddingValues())
                .padding(horizontal = 16.dp)
        ) {

            // Header estilo WhatsApp
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ia),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Orizzonter IA",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "En línea",
                        fontSize = 14.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Lista de mensajes
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(messages) { msg ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (msg.isUser) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = if (msg.isUser) userBubbleColor else bubbleColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .widthIn(max = 280.dp)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = msg.text,
                                color = textColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
                            )
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = if (msg.isUser) Alignment.BottomEnd else Alignment.BottomStart
                            ) {
                                Text(
                                    text = msg.time,
                                    color = textColor.copy(alpha = 0.5f),
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }
                }
            }

            // Campo de entrada y botón
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Escribe un mensaje...") },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.15f))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 4.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    singleLine = true,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = {
                        if (inputText.text.isNotBlank()) {
                            val currentTime = LocalTime.now().format(formatter)
                            messages.add(ChatMessage(inputText.text, true, currentTime))
                            inputText = TextFieldValue()
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
