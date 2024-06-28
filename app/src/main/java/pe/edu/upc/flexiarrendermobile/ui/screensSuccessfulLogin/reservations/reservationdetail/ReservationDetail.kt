package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.reservations.reservationdetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flexiarrendermobile.model.data.ReservationState

@Composable
fun ReservationDetail(
    reservation: ReservationState,
    popBack: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            BackgroundShapes()

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .widthIn(min = 300.dp, max = 400.dp)
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Detalle de la reserva",
                            style = TextStyle(
                                color = Color(0xFF6C63FF),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        ReservationDetailItem(
                            icon = Icons.Default.Home,
                            label = "Tu Habitación",
                            value = reservation.roomTitle.value
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        ReservationDetailItem(
                            icon = Icons.Default.DateRange,
                            label = "Fecha de inicio",
                            value = reservation.hourInit.value
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        ReservationDetailItem(
                            icon = Icons.Default.DateRange,
                            label = "Fecha de fin",
                            value = reservation.hourFinal.value
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        ReservationDetailItem(
                            icon = Icons.Default.Email,
                            label = "Correo del estudiante",
                            value = reservation.email.value
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        ReservationDetailItem(
                            icon = Icons.Default.Phone,
                            label = "Teléfono del estudiante",
                            value = reservation.phone.value
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        ReservationDetailItem(
                            icon = Icons.Default.CheckCircle,
                            label = "Observaciones del estudiante",
                            value = reservation.observation.value
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        Button(
                            onClick = { popBack() },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color(0xFF6C63FF)
                            )
                        ) {
                            Text("Regresar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReservationDetailItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF6C63FF),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = TextStyle(
                    color = Color(0xFF6C63FF),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = value,
                style = TextStyle(
                    color = Color(0xFF6C63FF),
                    fontSize = 17.sp
                )
            )
        }
    }
}

@Composable
fun BackgroundShapes() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Draw a gradient circle at the top left
        translate(left = -width * 0.5f, top = -height * 0.5f) {
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    shader = RadialGradientShader(
                        colors = listOf(Color(0xFF846CD9), Color.Transparent),
                        center = Offset(width / 2, height / 2),
                        radius = height / 1.5f
                    )
                }
                canvas.drawCircle(Offset(width / 2, height / 2), height / 1.5f, paint)
            }
        }

        // Draw a wave pattern at the bottom
        val wavePath = Path().apply {
            moveTo(0f, height)
            cubicTo(
                width * 0.25f, height * 0.8f,
                width * 0.75f, height * 1.2f,
                width, height
            )
            lineTo(width, height)
            close()
        }
        drawPath(
            path = wavePath,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF846CD9), Color.Transparent)
            )
        )
    }
}
