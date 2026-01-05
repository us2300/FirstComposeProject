package com.example.firstcomposeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeproject.ui.theme.FirstComposeProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FirstComposeProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactDetails(
                        getMockContactWithPicture(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactDetailsFavoriteWithPicturePreview() {
    FirstComposeProjectTheme {
        ContactDetails(getMockContactWithPicture(), Modifier)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactDetailsNotFavoriteNoPicturePreview() {
    FirstComposeProjectTheme {
        ContactDetails(getMockContactNoPicture(), Modifier)
    }
}

@Composable
fun ContactDetails(contact: Contact, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ProfilePicture(contact)
        FullNameAndFavorite(contact)
        InfoColumn(contact)
    }
}

@Composable
fun ProfilePicture(contact: Contact) {
    val contactInitials = "${contact.name.first()}${contact.surname?.first()}"

    Box(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (contact.imageRes == null) {
            Icon(
                painter = painterResource(id = R.drawable.circle),
                tint = Color.LightGray,
                contentDescription = null
            )
            Text(
                text = contactInitials
            )
        } else {
            Image(
                painter = painterResource(contact.imageRes),
                contentDescription = null
            )
        }
    }

}

@Composable
fun FullNameAndFavorite(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Имя Отчество
        Text(
            text = "${contact.name}  ${contact.surname ?: ""}",
            style = TextStyle(fontSize = 24.sp),
            fontWeight = FontWeight.Bold
        )
        // Фамилия и признак избранного
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = contact.familyName,
                style = TextStyle(fontSize = 24.sp)
            )

            Image(
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null,
                Modifier.padding(start = 6.dp)
            )
        }
    }
}

@Composable
fun InfoColumn(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp)
    ) {
        InfoRow(
            labelResId = R.string.phone,
            value = contact.phone
        )

        InfoRow(
            labelResId = R.string.address,
            value = contact.address
        )

        if (!contact.email.isNullOrEmpty()) {
            InfoRow(
                labelResId = R.string.email,
                value = contact.email
            )
        }
    }
}

@Composable
fun InfoRow(@StringRes labelResId: Int, value: String) {
    val label = stringResource(id = labelResId)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textAlign = TextAlign.End
        )

        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.weight(1f)
        )
    }
}

private fun getMockContactNoPicture(): Contact {
    return Contact(
        name = "Евгений",
        surname = "Андреевич",
        familyName = "Лукашин",
        imageRes = null,
        isFavorite = true,
        phone = "+7 495 495 95 95",
        address = "г. Москва, 3-я улица Строителей, д.25, кв. 12",
        email = "Elukashin@practicum.ru"
    )
}

private fun getMockContactWithPicture(): Contact {
    return Contact(
        name = "Василий",
        surname = null,
        familyName = "Кузякин",
        imageRes = R.drawable.funny_cat,
        isFavorite = false,
        phone = "---",
        address = "Ивановская обрасть, дер. Крутово, д.4",
        email = null
    )
}