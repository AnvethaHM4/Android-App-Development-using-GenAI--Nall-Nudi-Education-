package com.example.nallanudi

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this) {
            tts.language = Locale.US
        }

        setContent {
            NallaNudiApp(tts)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }
}

data class WordItem(
    val english: String,
    val kannada: String,
    val meaning: String,
    val subject: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NallaNudiApp(tts: TextToSpeech) {

    val context = LocalContext.current

    var isLoggedIn by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    val words = listOf(

        WordItem(
            "Gravity",
            "ಗುರುತ್ವಾಕರ್ಷಣೆ",
            "Force pulling objects toward Earth",
            "Science"
        ),

        WordItem(
            "Photosynthesis",
            "ಪ್ರಕಾಶ ಸಂಶ್ಲೇಷಣೆ",
            "Plants prepare food using sunlight",
            "Science"
        ),

        WordItem(
            "Trigonometry",
            "ತ್ರಿಕೋನಮಿತಿ",
            "Study of triangles and angles",
            "Maths"
        ),

        WordItem(
            "Algebra",
            "ಬೀಜಗಣಿತ",
            "Math using symbols and variables",
            "Maths"
        ),

        WordItem(
            "Demand",
            "ಬೇಡಿಕೆ",
            "Need for a product",
            "Commerce"
        ),

        WordItem(
            "Profit",
            "ಲಾಭ",
            "Money earned after expenses",
            "Commerce"
        )
    )

    val savedWords = remember { mutableStateListOf<WordItem>() }

    var selectedScreen by remember { mutableStateOf("Home") }

    if (!isLoggedIn) {

        LoginScreen { user, mail ->

            username = user
            email = mail

            isLoggedIn = true

            Toast.makeText(
                context,
                "Login Successful",
                Toast.LENGTH_SHORT
            ).show()
        }

    } else {

        Scaffold(

            topBar = {

                TopAppBar(
                    title = {
                        Text("Nalla-Nudi")
                    }
                )
            },

            bottomBar = {

                NavigationBar {

                    NavigationBarItem(
                        selected = selectedScreen == "Home",
                        onClick = {
                            selectedScreen = "Home"
                        },
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text("Home")
                        }
                    )

                    NavigationBarItem(
                        selected = selectedScreen == "Saved",
                        onClick = {
                            selectedScreen = "Saved"
                        },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text("Saved")
                        }
                    )

                    NavigationBarItem(
                        selected = selectedScreen == "Profile",
                        onClick = {
                            selectedScreen = "Profile"
                        },
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text("Profile")
                        }
                    )
                }
            }

        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFE0EAFC),
                                Color(0xFFCFDEF3)
                            )
                        )
                    )
            ) {

                when (selectedScreen) {

                    "Home" -> {

                        HomeScreen(
                            words = words,
                            savedWords = savedWords,
                            tts = tts
                        )
                    }

                    "Saved" -> {

                        SavedScreen(savedWords)
                    }

                    "Profile" -> {

                        ProfileScreen(
                            username = username,
                            email = email
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit
) {

    var username by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF2563EB),
                        Color(0xFF7C3AED)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF3EDF7)
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to Continue",
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = {
                        Text("Username")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    visualTransformation =
                        PasswordVisualTransformation(),
                    label = {
                        Text("Password")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                        if (
                            username.isNotEmpty() &&
                            email.isNotEmpty() &&
                            password.isNotEmpty()
                        ) {

                            onLogin(username, email)
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A4BBC)
                    )
                ) {

                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    words: List<WordItem>,
    savedWords: MutableList<WordItem>,
    tts: TextToSpeech
) {

    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }

    var selectedSubject by remember { mutableStateOf("All") }

    val filteredWords = words.filter {

        val matchesSearch =
            it.english.contains(searchText, true)

        val matchesSubject =
            selectedSubject == "All" ||
                    it.subject == selectedSubject

        matchesSearch && matchesSubject
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3163E0)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Word of the Day",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Gravity",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "ಗುರುತ್ವಾಕರ್ಷಣೆ",
                        color = Color.White,
                        fontSize = 22.sp
                    )

                    Text(
                        text = "Force pulling objects toward Earth",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Search Word")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement =
                    Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                FilterChipButton(
                    text = "All",
                    selected = selectedSubject == "All"
                ) {
                    selectedSubject = "All"
                }

                FilterChipButton(
                    text = "Science",
                    selected = selectedSubject == "Science"
                ) {
                    selectedSubject = "Science"
                }

                FilterChipButton(
                    text = "Maths",
                    selected = selectedSubject == "Maths"
                ) {
                    selectedSubject = "Maths"
                }

                FilterChipButton(
                    text = "Commerce",
                    selected = selectedSubject == "Commerce"
                ) {
                    selectedSubject = "Commerce"
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        items(filteredWords) { word ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF3EDF7)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = word.english,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = word.kannada,
                        fontSize = 24.sp,
                        color = Color(0xFF15803D)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = word.meaning
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement =
                            Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Button(
                            onClick = {

                                tts.speak(
                                    word.english,
                                    TextToSpeech.QUEUE_FLUSH,
                                    null,
                                    null
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF6A4BBC)
                            )
                        ) {

                            Icon(
                                Icons.Default.List,
                                contentDescription = ""
                            )

                            Spacer(
                                modifier = Modifier.width(4.dp)
                            )

                            Text("Voice")
                        }

                        Button(
                            onClick = {

                                if (!savedWords.contains(word)) {

                                    savedWords.add(word)

                                    Toast.makeText(
                                        context,
                                        "Saved",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF6A4BBC)
                            )
                        ) {

                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = ""
                            )

                            Spacer(
                                modifier = Modifier.width(4.dp)
                            )

                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedScreen(savedWords: List<WordItem>) {

    var currentIndex by remember { mutableStateOf(0) }

    var showAnswer by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Flashcard Revision",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (savedWords.isEmpty()) {

            Text(
                text = "No Saved Words",
                fontSize = 20.sp,
                color = Color.Gray
            )

        } else {

            val word = savedWords[currentIndex]

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable {

                        showAnswer = !showAnswer
                    },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEDE7F6)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    if (!showAnswer) {

                        Text(
                            text = word.english,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2563EB)
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text =
                                "Tap Card to Reveal Meaning",
                            color = Color.Gray
                        )

                    } else {

                        Text(
                            text = word.english,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2563EB)
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = word.kannada,
                            fontSize = 28.sp,
                            color = Color(0xFF15803D)
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = word.meaning,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {

                    currentIndex =
                        (currentIndex + 1) % savedWords.size

                    showAnswer = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A4BBC)
                )
            ) {

                Text("Next Flashcard")
            }
        }
    }
}

@Composable
fun ProfileScreen(
    username: String,
    email: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Icon(
                    Icons.Default.Person,
                    contentDescription = "",
                    modifier = Modifier.size(90.dp),
                    tint = Color(0xFF6A4BBC)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = username,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = email,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Student User",
                    color = Color(0xFF2563EB)
                )
            }
        }
    }
}

@Composable
fun FilterChipButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (selected)
                    Color(0xFF2563EB)
                else
                    Color.LightGray
        )
    ) {

        Text(text)
    }
}