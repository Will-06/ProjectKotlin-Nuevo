package com.orizzonter.app.features.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.orizzonter.app.R

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage("Bienvenido", "Explora el mundo del ciclismo con Orizzonter", R.drawable.rutas),
        OnboardingPage("Rutas", "Descubre las mejores rutas para ciclistas", R.drawable.like),
        OnboardingPage("Comunidad", "Conéctate con otros ciclistas", R.drawable.comunidad)
    )

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            // Pasar si es última página
            OnboardingPageContent(
                page = pages[page],
                isLastPage = (page == pages.size - 1),
                onStartClick = { navController.navigate("login") }
            )
        }

        DotsIndicator(
            totalDots = pages.size,
            selectedIndex = pagerState.currentPage
        )

        Spacer(modifier = Modifier.height(24.dp)) // Margen inferior
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage, isLastPage: Boolean, onStartClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        if (isLastPage) {
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = onStartClick,
                modifier = Modifier
            ) {
                Text("Comenzar")
            }
        }
    }
}


@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color = color)
            )
        }
    }
}
