package souza.prospero.henrique.eduardo.firstappjc

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import souza.prospero.henrique.eduardo.firstappjc.ui.theme.FirstAppJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAppJCTheme {
                SendMail()
            }
        }
    }
}

data class EmailData(
    var email: String = "",
    var subject: String = "",
    var body: String = ""
)

@Composable
fun SendMail() {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val context = LocalContext.current


    if (isPortrait) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxSize()
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.email_label)) },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text(stringResource(id = R.string.subject_label)) },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = body,
                onValueChange = { body = it },
                label = { Text(stringResource(id = R.string.body_label)) },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.fillMaxHeight())
            SendButton(email, subject, body, context)
        }
    } else {
        Row(modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxSize()) {
            Column(modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp)) {
                Text(stringResource(id = R.string.title), modifier = Modifier.padding(bottom = 16.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(id = R.string.email_label)) },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
                TextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text(stringResource(id = R.string.subject_label)) },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
                TextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text(stringResource(id = R.string.body_label)) },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally) {

                SendButton(email, subject, body, context)
            }
        }
    }
}


@Composable
fun SendButton(email: String, subject: String, body: String, context: Context) {
    Button(
        onClick = { sendEmail(email, subject, body, context) },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(stringResource(id = R.string.send_mail_button))
    }
}

private fun sendEmail(email: String, subject: String, body: String, context: Context) {
    val sendMailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(sendMailIntent, "Send email to ${email}"))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
    }
}

@Composable
@Preview(showBackground = true)
fun SendMailPreview() {
    FirstAppJCTheme {
        SendMail()
    }
}
