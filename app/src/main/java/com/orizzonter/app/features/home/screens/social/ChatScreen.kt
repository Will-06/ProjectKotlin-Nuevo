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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    val messages = remember {
        mutableStateListOf(
            "Tú: Hola, ¿me puedes recomendar rutas?",
            "IA: ¡Hola! Claro que sí. ¿Cómo estás?",
            "IA: ¿Te puedo hacer una pregunta para ayudarte mejor?",
            "Tú: Sí, claro.",
            "IA: Perfecto. ¿Qué tipo de rutas de ciclismo te interesan? ¿Montaña, carretera, algo fácil o más desafiante?",
            "Tú: Estoy buscando rutas para ciclismo de montaña cerca de mí.",
            "IA: ¡Genial! Tengo varias opciones cerca de tu ubicación. ¿Quieres que te cuente sobre la dificultad y la distancia de cada ruta?",
            "Tú: Sí, por favor. También me interesa saber si hay subidas exigentes.",
            "IA: Por supuesto. La ruta 'Cerro Verde Trail' es de dificultad alta, con varias pendientes pronunciadas y 18 km en total.",
            "Tú: ¿Hay algún taller cercano en caso de que tenga un problema con la bici?",
            "IA: Sí, el taller 'BikeFix' está a 2 km de la ruta, abre de 8am a 6pm y tiene buenas reseñas.",
            "Tú: ¡Perfecto! ¿Qué me recomiendas comer antes de iniciar la ruta?",
            "IA: Lo mejor es algo ligero y energético, como avena con plátano o pan integral con miel 🍯.",
            "Tú: ¿Puedo compartir mis rutas con otros ciclistas?",
            "IA: Sí, en tu perfil puedes compartir rutas, comentar y ver las publicaciones de otros ciclistas 🧭.",
            "Tú: Gracias, ha sido muy útil.",
            "IA: ¡De nada! Estoy aquí para ayudarte cuando lo necesites. ¡Disfruta tu ruta! 🚴‍♀️"
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
        // Aquí se quitó el floatingActionButton del botón regresar
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(WindowInsets.ime.asPaddingValues())
                .padding(horizontal = 16.dp)
        ) {

            // Encabezado estilo WhatsApp
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ia), // <- Usa tu drawable aquí
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
                        color = Color(0xFF4CAF50), // Verde tipo WhatsApp
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
                    val isUser = msg.startsWith("Tú:")
                    val displayMsg = if (isUser) msg.removePrefix("Tú:").trim() else msg

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Text(
                            text = displayMsg,
                            color = textColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .background(
                                    color = if (isUser) userBubbleColor else bubbleColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(14.dp)
                                // Aquí agrego el ancho máximo para que no se extiendan demasiado
                                .widthIn(max = 280.dp) // aprox 70-75% de pantalla en la mayoría de móviles
                        )
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
                            messages.add("Tú: ${inputText.text}")
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
