package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.reservations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Reservation() {

    Scaffold(
        containerColor = Color.White,
    ) {
        Column(
            modifier= Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Spacer(modifier = Modifier.height(56.dp))
            Spacer(modifier = Modifier.height(300.dp))

            Text(
                text = "Actualmente tus habitaciones no tienen reservas",
                style= TextStyle( color = Color(0xFF846CD9), fontSize = 20.sp),
                modifier=Modifier.padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 17.sp
            )
        }

    }

}