package pe.edu.upc.flexiarrendermobile.ui.home

import android.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrender
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSecondStep
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSignUpFirstStep


@Composable
fun Home(){
    //navegacion
    val navController= rememberNavController()


    //request sign up
    val requestSignUpArrender = RequestSignUpArrender()

    val errorMessage = remember { mutableStateOf<String?>(null) }









    NavHost(navController = navController, startDestination=Routes.SignUpFirstStep.route){
        composable(Routes.SignUpFirstStep.route){
            SignUpSignUpFirstStep(requestSignUpArrender,errorMessage){

                navController.navigate(Routes.SignUpSecondStep.route)
            }
        }

        composable(Routes.SignUpSecondStep.route){
            SignUpSecondStep( requestSignUpArrender,errorMessage,pressOnBack={
                navController.popBackStack()
            })
        }
    }
}

sealed class Routes(val route:String){
    data object SignIn: Routes("SignIn")
    data object SignUpFirstStep: Routes("SignUpFirstStep")
    data object SignUpSecondStep: Routes("SignUpSecondStep")

}




