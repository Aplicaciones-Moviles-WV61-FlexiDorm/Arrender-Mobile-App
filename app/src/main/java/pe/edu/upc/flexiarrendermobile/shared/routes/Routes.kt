package pe.edu.upc.flexiarrendermobile.shared.routes

sealed class Routes(val route:String){
    data object SignIn: Routes("SignIn")
    data object SignUpFirstStep: Routes("SignUpFirstStep")
    data object SignUpSecondStep: Routes("SignUpSecondStep")


    data object NavDrawer: Routes("NavDrawer")

    data object RoomList: Routes("RoomList")

    data object RoomDetail: Routes("RoomDetail")

    data object Profile: Routes("Profile")

    data object UpdateProfile: Routes("UpdateProfile")

    data object Reservation: Routes("Reservation")

}