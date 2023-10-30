package com.example.temperature_convertor_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temperature_convertor_app.events.ViewState
import com.example.temperature_convertor_app.model.TemperatureViewModel
import com.example.temperature_convertor_app.presentation.ui.theme.Temperature_Convertor_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Temperature_Convertor_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewModel = viewModel<TemperatureViewModel>()
                    val state = viewModel.state
                    ConvertorScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
                        onConvert = viewModel::onConvert,
                        state = state
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Temperature_Convertor_AppTheme {

    }
}