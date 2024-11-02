package com.example.bumpbeats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bumpbeats.ui.theme.BumpBeatsTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.shadow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BumpBeatsTheme {
                WelcomeScreen(
                    onSignInClick = { /* Navigate to Sign In screen */ },
                    onSignUpClick = { /* Navigate to Sign Up screen */ }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Background image with reduced opacity and scaled to fill screen
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop, // Ensures the image fills the screen
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.9f) // Set transparency level here
                )

                // Main content layered above background image
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.logo_purple).copy(alpha = 0.7f)) // Optional overlay color with transparency
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Enlarged logo positioned at the top
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Pushes the buttons to the bottom

                    // Sign In and Sign Up buttons at the bottom with shadows
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Button(
                            onClick = onSignInClick,
                            modifier = Modifier
                                .width(140.dp)
                                .height(45.dp)
                                .shadow(8.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Sign In", color = Color.White)
                        }
                        Button(
                            onClick = onSignUpClick,
                            modifier = Modifier
                                .width(140.dp)
                                .height(45.dp)
                                .shadow(8.dp, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Sign Up", color = Color.White)
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    BumpBeatsTheme {
        WelcomeScreen(
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}
