package br.com.aranoua.solofacil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aranoua.solofacil.ui.theme.SoloFacilTheme

data class ProfileMenuItem(val title: String, val icon: ImageVector)

@Composable
fun ProfileScreen() {
    val menuItems = listOf(
        ProfileMenuItem("Notificações", Icons.Default.Notifications),
        ProfileMenuItem("Idioma", Icons.Filled.Language),
        ProfileMenuItem("Sobre o App", Icons.Default.Info),
        ProfileMenuItem("Sair", Icons.Default.ExitToApp)
    )

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(32.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                LazyColumn {
                    items(menuItems) { item ->
                        ProfileMenuItemRow(item = item, onClick = { /* Ação do menu */ })
                        if (item != menuItems.last()) {
                           Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle, 
            contentDescription = "Foto de Perfil",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "João Silva", style = MaterialTheme.typography.titleLarge)
            Text(text = "Humaitá, AM", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ProfileMenuItemRow(item: ProfileMenuItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = item.icon, contentDescription = item.title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SoloFacilTheme {
        ProfileScreen()
    }
}
