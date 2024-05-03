package pe.edu.upc.flexiarrendermobile.ui.home

import android.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.ui.room.roomdetail.RoomDetail
import pe.edu.upc.flexiarrendermobile.ui.room.roomlist.RoomList
import pe.edu.upc.flexiarrendermobile.ui.signin.SignIn
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSecondStep
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSignUpFirstStep


@Composable
fun Home(){
    //navegacion
    val navController= rememberNavController()


    //request sign up
    val requestSignUpArrender = RequestSignUpArrenderState()

    val errorMessage = remember { mutableStateOf<String?>(null) }









    NavHost(navController = navController, startDestination=Routes.SignIn.route){
        composable(Routes.SignUpFirstStep.route){
            SignUpSignUpFirstStep(
                requestSignUpArrender,
                errorMessage,
                secontStep= {
                    navController.navigate(Routes.SignUpSecondStep.route) },
                signInStep = {
                    navController.navigate(Routes.SignIn.route)}
            )
        }


        composable(Routes.SignUpSecondStep.route){
            SignUpSecondStep(
                requestSignUpArrender,
                errorMessage,
                pressOnBack={
                navController.popBackStack() },
                navigationToSignIn={
                    navController.navigate(Routes.SignIn.route)
                }

            )
        }

        composable(Routes.SignIn.route){
            SignIn(
                errorMessage,
                sinUpFirstStep={
                    navController.navigate(Routes.SignUpFirstStep.route)
                },
                signInSuccessful={
                    navController.navigate(Routes.RoomList.route)
                }
            )
        }

        composable(Routes.RoomList.route){
            RoomList(
                addRoom={
                    navController.navigate(Routes.RoomDetail.route)
                }
            )
        }

        composable(Routes.RoomDetail.route){
            RoomDetail(errorMessage, finishAddRoom={
                navController.navigate(Routes.RoomList.route)
            })
        }

    }
}

sealed class Routes(val route:String){
    data object SignIn: Routes("SignIn")
    data object SignUpFirstStep: Routes("SignUpFirstStep")
    data object SignUpSecondStep: Routes("SignUpSecondStep")

    data object RoomList: Routes("RoomList")

    data object RoomDetail: Routes("RoomDetail")

}


//Meter el preview




