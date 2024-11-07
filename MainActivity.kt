package com.example.bumpbeats
import android.os.Bundle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Cake
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bumpbeats.ui.theme.BumpBeatsTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BumpBeatsTheme {
                WelcomeScreen(
                    onSignInClick = { navigateToSignInScreen() },
                    onSignUpClick = { navigateToSignUpScreen() }
                )
            }
        }
    }

    private fun navigateToSignInScreen() {
        setContent {
            BumpBeatsTheme {
                SignInScreen()
            }
        }
    }

    private fun navigateToSignUpScreen() {
        setContent {
            BumpBeatsTheme {
                SignUpScreen()
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
                Image(
                    painter = painterResource(id = R.drawable.backgroundapp),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.9f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.logo_purple).copy(alpha = 0.7f))
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

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
                                .height(45.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Sign In", color = Color.White)
                        }
                        Button(
                            onClick = onSignUpClick,
                            modifier = Modifier
                                .width(140.dp)
                                .height(45.dp),
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

@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sign_up_background),
                    contentDescription = "Sign In Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.9f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(280.dp)
                            .padding(bottom = 5.dp)
                    )

                    OutlinedTextFieldWithError(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Default.Email,
                        isError = false,
                        errorMessage = null
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextFieldWithError(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        isError = false,
                        errorMessage = null,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            signInUser(email, password, onSuccess = {
                                errorMessage = "Sign-in successful!"
                            }, onError = { error ->
                                errorMessage = error
                            })
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Sign In", color = Color.White)
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    )
}

fun signInUser(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                val errorMessage = getFirebaseErrorMessageSignIn(task.exception)
                onError(errorMessage)
            }
        }
}

fun getFirebaseErrorMessageSignIn(exception: Exception?): String {
    val message = exception?.message ?: return "Unknown error occurred. Please try again."

    return when {
        message.contains("no user record", ignoreCase = true) ->
            "This email is not registered. Please check your email or sign up."
        message.contains("password is invalid", ignoreCase = true) ->
            "Incorrect password. Please try again."
        message.contains("email address is badly formatted", ignoreCase = true) ->
            "The email address is badly formatted."
        message.contains("too many requests", ignoreCase = true) ->
            "Too many attempts. Please try again later."
        else -> message // Returns the default Firebase error if no specific match
    }
}

@Composable
fun SignUpScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var birthDateError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(false) }
    var passwordStrength by remember { mutableStateOf("") }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sign_up_background),
                    contentDescription = "Sign Up Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.9f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // App Logo Image
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(280.dp)
                            .padding(bottom = 5.dp) // Adjust padding as needed
                    )
                    // Email Field with Icon and Error Handling
                    OutlinedTextFieldWithError(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Default.Email,
                        isError = emailError,
                        errorMessage = if (emailError) "Enter a valid email address" else null
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Password Field with Visibility Toggle, Strength Indicator, and Error Handling
                    OutlinedTextFieldWithError(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordStrength = getPasswordStrength(it)
                        },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        isError = passwordError,
                        errorMessage = if (passwordError) "Password does not meet requirements" else null,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )
                    // Password Strength Indicator
                    if (passwordStrength.isNotEmpty()) {
                        Text(text = "Password Strength: $passwordStrength", color = getPasswordStrengthColor(passwordStrength))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // First Name Field
                    OutlinedTextFieldWithError(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "First Name",
                        icon = Icons.Default.Person,
                        isError = firstNameError,
                        errorMessage = if (firstNameError) "First name cannot be empty" else null
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Last Name Field
                    OutlinedTextFieldWithError(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Last Name",
                        icon = Icons.Default.Person,
                        isError = lastNameError,
                        errorMessage = if (lastNameError) "Last name cannot be empty" else null
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Birth Date Field with Date Picker
                    OutlinedTextFieldWithError(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        label = "Birth Date",
                        icon = Icons.Default.Cake,
                        isError = birthDateError,
                        errorMessage = if (birthDateError) "Please select a valid birth date" else null,
                        trailingIcon = {
                            IconButton(onClick = { /* Show Date Picker */ }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Terms and Conditions Checkbox
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = agreedToTerms,
                            onCheckedChange = { agreedToTerms = it }
                        )
                        Text("I agree to the Terms and Conditions", Modifier.padding(start = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign Up Button with Conditional Enabling
                    Button(
                        onClick = {
                            signUpUser(
                                email = email,
                                password = password,
                                firstName = firstName,
                                lastName = lastName,
                                birthDate = birthDate,
                                onSuccess = {
                                    errorMessage = "Sign-up successful!"
                                },
                                onError = { error ->
                                    errorMessage = error
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8E2DE2) // Start color of gradient
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = agreedToTerms
                    ) {
                        Text("Sign Up", color = Color.White)
                    }

                    // Overall Error Message
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    )
}

fun signUpUser(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    birthDate: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                // Create a user object to store additional information
                val user = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "birthDate" to birthDate,
                    "email" to email
                )

                db.collection("users").document(userId).set(user)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        onError("Failed to store user details: ${exception.message}")
                    }
            } else {
                // Use a helper function to map exception to a user-friendly error message
                val errorMessage = getFirebaseErrorMessageSignUp(task.exception)
                onError(errorMessage)
            }
        }
        .addOnFailureListener { exception ->
            onError("Unexpected error: ${exception.message}")
        }
}

fun getFirebaseErrorMessageSignUp(exception: Exception?): String {
    val message = exception?.message ?: return "Unknown error occurred. Please try again."

    return when {
        message.contains("email address is badly formatted", ignoreCase = true) ->
            "The email address is badly formatted."
        message.contains("email address is already in use", ignoreCase = true) ->
            "The email address is already in use by another account."
        message.contains("password is too weak", ignoreCase = true) ->
            "The password is too weak. Please use at least 11 characters with a mix of letters, numbers, and symbols."
        message.contains("user account has been disabled", ignoreCase = true) ->
            "This user account has been disabled. Please contact support."
        message.contains("operation not allowed", ignoreCase = true) ->
            "This operation is not allowed. Please contact support."
        message.contains("too many requests", ignoreCase = true) ->
            "Too many attempts. Please try again later."
        else -> message // Return the original message if no match is found
    }
}

@Composable
fun OutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isError: Boolean,
    errorMessage: String?,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
        errorMessage?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Helper function to determine password strength based on Firebase requirements
fun getPasswordStrength(password: String): String {
    if (password.isEmpty()) return "" // Return empty when there’s no input

    return when {
        password.length >= 12 && hasUppercase(password) && hasSpecialCharacter(password) -> "Strong"
        password.length == 11 -> "Medium"
        else -> "Weak"
    }
}

// Checks if the password contains at least one uppercase letter
fun hasUppercase(password: String) = password.any { it.isUpperCase() }

// Checks if the password contains at least one special character
fun hasSpecialCharacter(password: String): Boolean {
    val specialChars = "!@#\$%^&*()_+[]{}|;:,.<>?"
    return password.any { it in specialChars }
}

fun getPasswordStrengthColor(strength: String): Color {
    return when (strength) {
        "Strong" -> Color.Green
        "Medium" -> Color.Yellow
        "Weak" -> Color.Red
        else -> Color.Gray
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    BumpBeatsTheme {
        WelcomeScreen(
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    BumpBeatsTheme {
        SignInScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    BumpBeatsTheme {
        SignUpScreen()
    }
}
