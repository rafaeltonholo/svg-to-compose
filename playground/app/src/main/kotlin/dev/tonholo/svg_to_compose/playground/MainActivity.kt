package dev.tonholo.svg_to_compose.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.tonholo.svg_to_compose.playground.ui.icon.svg.Brasil
import dev.tonholo.svg_to_compose.playground.ui.theme.SampleAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text("SVG/XML to Compose Playground App")
                            }
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(text = "From Android Studio import tool (drawable):")
                        Image(painter = painterResource(R.drawable.brasil), contentDescription = null)
                        Text(text = "From SVG to Compose Plugin (ImageVector):")
                        Image(imageVector = Brasil, contentDescription = null)
                    }
                }
            }
        }
    }
}
