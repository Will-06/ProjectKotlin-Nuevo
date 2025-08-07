package com.orizzonter.app.features.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.R
import androidx.compose.ui.text.font.FontFamily

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
            OnboardingPageContent(
                page = pages[page],
                isLastPage = page == pages.lastIndex,
                onStartClick = { navController.navigate("login") }
            )
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    isLastPage: Boolean,
    onStartClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(bottomStart = 55.dp, bottomEnd = 55.dp))
                    .background(page.topColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = page.imageRes),
                    contentDescription = page.title,
                    modifier = Modifier.size(500.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp), // Puedes ajustar esto para moverlo más o menos
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = page.title,
                    fontSize = 34.sp, // ligeramente más pequeño que 36sp, más armonioso
                    fontWeight = FontWeight.Bold, // ExtraBold puede parecer demasiado pesado
                    letterSpacing = (-0.5).sp, // sutil compresión para elegancia
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif, // Serif para un toque profesional y editorial
                    lineHeight = 40.sp, // buena proporción para lectura y estética
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .widthIn(max = 320.dp)
                )


                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = page.description,
                    fontSize = 16.sp, // un poco más chico para mejor jerarquía
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    color = Color(0xFF6B6B6B),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .widthIn(max = 320.dp)
                )
            }
        }

        if (isLastPage) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 33.dp, bottom = 60.dp)
                    .size(65.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(page.topColor),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(containerColor = page.topColor),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.img), // Reemplaza con tu ícono
                        contentDescription = "Empezar",
                        modifier = Modifier.size(32.dp) // Ajusta el tamaño según necesidad
                    )
                }

            }
        }
    }
}
