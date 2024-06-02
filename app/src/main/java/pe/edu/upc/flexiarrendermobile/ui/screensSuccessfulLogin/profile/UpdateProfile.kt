package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.local.ArrenderEntity

@Composable
fun UpdateProfile(popBack:()->Unit) {


    Scaffold(
        containerColor = Color.White
    ) {
        Column(
            modifier= Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(56.dp))
            var arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository("")

            var arrender= arrenderRepository.getArrender()
            var arrenderEntity=arrender[0]
            ProfileHeaderUpdate(arrenderEntity.profilePicture)
            ProfileContentUpdate(arrenderEntity)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    shape= RoundedCornerShape(8.dp),
                    colors= ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                    onClick = { popBack() }
                ) {
                    Text("Guardar")

                }

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    shape= RoundedCornerShape(8.dp),
                    colors= ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color.Red),
                    onClick = { popBack() }
                ) {
                    Text("Cancelar")
                }
            }
        }
    }

}

@Composable
fun ProfileHeaderUpdate(url:String) {
    ProfileImageUpdate(url)
}

@Composable
fun ProfileImageUpdate(url: String) {
    Box(
        modifier = Modifier
            .size(128.dp) // Tamaño del contenedor circular
            .clip(CircleShape)
            .background(Color.Gray) // Color de fondo por si la imagen no se carga
            .border(2.dp, Color(0xFF846CD9), CircleShape) // Añadir borde de color

    ) {
        GlideImage(
            imageModel = { url },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProfileContentUpdate(arrenderEntity: ArrenderEntity){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        InputFieldUpdate(label = "Nombre",arrenderEntity.firstname)
        InputFieldUpdate(label = "Apellido Paterno",arrenderEntity.lastname)
        InputFieldUpdate(label = "Username",arrenderEntity.username)
        InputFieldUpdate(label = "Domicilio",arrenderEntity.address)
        InputFieldUpdate(label = "Telefono",arrenderEntity.phoneNumber)
    }
}
@Composable
fun InputFieldUpdate(label: String, data: String, isEditable: Boolean = true) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = Color(0xFF846CD9),
            modifier = Modifier.padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = data,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
            shape = RoundedCornerShape(15.dp), // Redondear los bordes
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF846CD9),
                disabledTextColor = Color(0xFF846CD9),
                disabledBorderColor = Color(0xFF846CD9),
                disabledLabelColor = Color(0xFF846CD9)
            ),
            enabled = isEditable
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
