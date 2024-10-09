package souza.prospero.henrique.eduardo.firstappjc

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import souza.prospero.henrique.eduardo.firstappjc.ui.theme.FirstAppJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppJCTheme {
                SendMail()
            }
        }
    }
}

@Composable
fun SendMail() {
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(all = 16.dp)
    ) {
        Text("Send Mail using Gmail")
        Column(verticalArrangement = Arrangement.Top) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Body") },
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(
            onClick = {
                // Criação do Intent ao clicar no botão
                val sendMailIntent: Intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$email") // Apenas para email
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                }

                try {
                    context.startActivity(Intent.createChooser(sendMailIntent, "Send email to $email"))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Send")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SendMailPreview() {
    FirstAppJCTheme {
        SendMail()
    }
}
