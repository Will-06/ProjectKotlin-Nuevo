package com.orizzonter.app.features.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.R
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int,
    val topColor: Color
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            "Bienvenido",
            "Empieza tu aventura sobre dos ruedas con Orizzonter, la app pensada para los verdaderos amantes del ciclismo. Descubre rutas, conoce nuevos destinos y transforma cada pedaleo en una experiencia única.",
            R.drawable.bicicle,
            Color(0xFF4DB6AC)
        ),
        OnboardingPage(
            "Rutas",
            "Pedalea por caminos increíbles que te esperan a la vuelta de cada curva. Con Orizzonter, tendrás acceso a una amplia variedad de rutas adaptadas a tu nivel, intereses y ganas de explorar.",
            R.drawable.like,
            Color(0xFFFFC107)
        ),
        OnboardingPage(
            "Comunidad",
            "Súmate a una comunidad vibrante que comparte tu pasión por el ciclismo. Intercambia experiencias, únete a retos, encuentra compañeros de ruta y crece junto a otros ciclistas como tú.",
            R.drawable.comunidad,
            Color(0xFF9575CD)
        )
    )

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingPageContent(page = pages[page])
            }

            PageIndicator()

            Spacer(modifier = Modifier.height(24.dp))

            NextButton(
                isLastPage = pagerState.currentPage == pages.lastIndex,
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < pages.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navController.navigate("login")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 100.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)) // color azul con transparencia
               // .border(
                   // width = 1.5.dp,
                   // color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                   // shape = RoundedCornerShape(bottomStart = 55.dp, bottomEnd = 55.dp)
                //)
                    ,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = page.title,
                modifier = Modifier.size(280.dp)
            )
        }


        // resto igual...
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 24.dp)
                .padding(top = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = page.title.uppercase(),  // Letras mayúsculas para autoridad y presencia
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.secondary,  // Un color contrastante pero armonioso
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,  // Moderno y limpio
                letterSpacing = 2.sp,  // Espaciado generoso para estilo y lectura
                lineHeight = 42.sp,
                modifier = Modifier
                    .widthIn(max = 320.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(4.dp),
                        ambientColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                        spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                    )
            )



            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = page.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                lineHeight = 28.sp,
                letterSpacing = 0.25.sp,
                modifier = Modifier.widthIn(max = 420.dp)
            )
        }


    }
}

@Composable
fun PageIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(25.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
        )
    }
}

@Composable
fun NextButton(
    isLastPage: Boolean,
    onClick: () -> Unit
) {
    val backgroundGlassColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.25f)
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val textColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 90.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundGlassColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLastPage) "¡Comencemos la aventura!" else "Siguiente",
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
