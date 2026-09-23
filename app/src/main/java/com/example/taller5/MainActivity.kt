package com.example.taller5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class Elemento(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val descripcionCorta: String,
    val descripcionLarga: String,
    val icono: String
)

val elementos = listOf(
    Elemento(1, "León", "Mamíferos", "El rey de la sabana africana.", "El león es un gran felino que vive en manadas en la sabana africana. Los machos se reconocen por su melena y las hembras son las principales cazadoras del grupo.", "🦁"),
    Elemento(2, "Elefante", "Mamíferos", "El animal terrestre más grande.", "El elefante africano puede pesar más de seis toneladas. Usa su trompa para beber, comer, comunicarse y hasta para bañarse con barro.", "🐘"),
    Elemento(3, "Delfín", "Mamíferos", "Mamífero marino muy inteligente.", "Los delfines viven en grupos llamados manadas, se comunican con silbidos y usan la ecolocalización para orientarse y cazar.", "🐬"),
    Elemento(4, "Murciélago", "Mamíferos", "El único mamífero que vuela.", "Los murciélagos son esenciales para polinizar plantas y controlar plagas de insectos. Muchos se orientan en la oscuridad gracias a la ecolocalización.", "🦇"),
    Elemento(5, "Águila", "Aves", "Ave rapaz de vista aguda.", "El águila puede ver presas a más de tres kilómetros de distancia. Construye nidos enormes en lo alto de árboles y acantilados.", "🦅"),
    Elemento(6, "Colibrí", "Aves", "Pequeño y veloz polinizador.", "El colibrí bate sus alas hasta 80 veces por segundo y es la única ave capaz de volar hacia atrás. Colombia es uno de los países con más especies.", "🐦"),
    Elemento(7, "Pingüino", "Aves", "Ave que nada pero no vuela.", "Los pingüinos viven principalmente en el hemisferio sur. Sus alas se transformaron en aletas que les permiten nadar con gran agilidad.", "🐧"),
    Elemento(8, "Búho", "Aves", "Cazador silencioso de la noche.", "El búho tiene plumas especiales que le permiten volar sin hacer ruido y puede girar la cabeza hasta 270 grados.", "🦉"),
    Elemento(9, "Cocodrilo", "Reptiles", "Reptil antiguo y poderoso.", "Los cocodrilos existen desde hace millones de años. Tienen una de las mordidas más fuertes del reino animal y viven en ríos y pantanos.", "🐊"),
    Elemento(10, "Tortuga", "Reptiles", "Lenta pero muy longeva.", "Algunas tortugas pueden vivir más de cien años. Su caparazón está unido a su esqueleto y les sirve de protección.", "🐢"),
    Elemento(11, "Serpiente", "Reptiles", "Reptil sin patas.", "Las serpientes se desplazan ondulando su cuerpo y detectan olores con la lengua. No todas son venenosas y muchas controlan poblaciones de roedores.", "🐍"),
    Elemento(12, "Camaleón", "Reptiles", "Maestro del cambio de color.", "El camaleón cambia de color para comunicarse y regular su temperatura. Sus ojos se mueven de forma independiente y su lengua es más larga que su cuerpo.", "🦎"),
    Elemento(13, "Pulpo", "Invertebrados", "Tiene tres corazones.", "El pulpo tiene ocho brazos, tres corazones y sangre azul. Es capaz de resolver problemas, abrir frascos y camuflarse en segundos.", "🐙"),
    Elemento(14, "Mariposa", "Invertebrados", "Insecto de alas coloridas.", "La mariposa pasa por una metamorfosis completa: huevo, oruga, crisálida y adulto. Es una gran polinizadora de flores.", "🦋")
)

val categorias = elementos.map { it.categoria }.distinct()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val listState = rememberLazyListState()
    var query by rememberSaveable { mutableStateOf("") }
    var categoriaSeleccionada by rememberSaveable { mutableStateOf<String?>(null) }

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            ListaScreen(
                elementos = elementos,
                query = query,
                onQueryChange = { query = it },
                categoriaSeleccionada = categoriaSeleccionada,
                onCategoriaChange = { categoriaSeleccionada = it },
                listState = listState,
                onElementoClick = { id ->
                    navController.navigate("detalle/$id") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = "detalle/{elementoId}",
            arguments = listOf(navArgument("elementoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("elementoId") ?: 0
            val elemento = elementos.firstOrNull { it.id == id }
            val volver = {
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navController.popBackStack()
                }
            }
            if (elemento != null) {
                DetalleScreen(elemento = elemento, onBack = { volver() })
            } else {
                Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    Text("Elemento no encontrado", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { volver() }) { Text("Volver") }
                }
            }
        }
    }
}

@Composable
fun ListaScreen(
    elementos: List<Elemento>,
    query: String,
    onQueryChange: (String) -> Unit,
    categoriaSeleccionada: String?,
    onCategoriaChange: (String?) -> Unit,
    listState: LazyListState,
    onElementoClick: (Int) -> Unit
) {
    val filtrados = remember(query, categoriaSeleccionada, elementos) {
        elementos.filter { elemento ->
            val coincideTexto = query.isBlank() || elemento.titulo.contains(query.trim(), ignoreCase = true)
            val coincideCategoria = categoriaSeleccionada == null || elemento.categoria == categoriaSeleccionada
            coincideTexto && coincideCategoria
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Buscar") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            item {
                FilterChip(
                    selected = categoriaSeleccionada == null,
                    onClick = { onCategoriaChange(null) },
                    label = { Text("Todas") }
                )
            }
            items(categorias, key = { it }) { categoria ->
                FilterChip(
                    selected = categoriaSeleccionada == categoria,
                    onClick = {
                        onCategoriaChange(if (categoriaSeleccionada == categoria) null else categoria)
                    },
                    label = { Text(categoria) }
                )
            }
        }
        Text(
            text = "${filtrados.size} de ${elementos.size} elementos",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
        if (filtrados.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay resultados para \"$query\"", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                items(filtrados, key = { it.id }) { elemento ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onElementoClick(elemento.id) }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icono(emoji = elemento.icono, tamano = 56)
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(elemento.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(elemento.descripcionCorta, style = MaterialTheme.typography.bodySmall)
                                Text(
                                    elemento.categoria,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Icono(emoji: String, tamano: Int) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.size(tamano.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(emoji, fontSize = (tamano / 2).sp)
        }
    }
}

@Composable
fun DetalleScreen(elemento: Elemento, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Icono(emoji = elemento.icono, tamano = 120)
        Spacer(Modifier.height(16.dp))
        Text(elemento.titulo, style = MaterialTheme.typography.headlineMedium)
        Text(elemento.categoria, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(16.dp))
        Text(elemento.descripcionLarga, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onBack) { Text("Volver") }
    }
}
