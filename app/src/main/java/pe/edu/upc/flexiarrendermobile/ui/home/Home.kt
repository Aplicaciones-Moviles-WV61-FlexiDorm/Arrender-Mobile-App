package pe.edu.upc.flexiarrendermobile.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.routes.Routes
import pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.navDrawer.NavDrawer
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

    val isLoading = remember { mutableStateOf(false) }

    // Estado para el evento de cerrar sesión
    val logoutEvent = remember { mutableStateOf(false) }

    // Navega al SignIn cuando se activa el evento de cerrar sesión
    LaunchedEffect(logoutEvent.value) {
        if (logoutEvent.value) {
            navController.navigate(Routes.SignIn.route) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

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
                },
                isLoading

            )
        }

        composable(Routes.SignIn.route){
            SignIn(
                errorMessage,
                sinUpFirstStep={
                    navController.navigate(Routes.SignUpFirstStep.route)
                },
                signInSuccessful={
                    navController.navigate(Routes.NavDrawer.route)
                },
                logoutEvent,
                isLoading
            )
        }

        composable(Routes.NavDrawer.route) {
            NavDrawer(logoutEvent = logoutEvent)
        }




    }
}

@Preview
@Composable
fun HomePreview(){
    Home()
}







