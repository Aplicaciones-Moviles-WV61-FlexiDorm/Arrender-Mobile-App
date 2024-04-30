package pe.edu.upc.flexiarrendermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrender
import pe.edu.upc.flexiarrendermobile.ui.home.Home
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlexiArrenderMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //var requestSignUpArrender: RequestSignUpArrender? =null

                   // if (requestSignUpArrender != null) {
                        Home()
                    //} else {
                       // Text("Hello, FlexiArrenderMobile!")
                  //  }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlexiArrenderMobileTheme {
        Greeting("Android")
    }
}