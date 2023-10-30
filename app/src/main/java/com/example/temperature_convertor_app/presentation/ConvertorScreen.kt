package com.example.temperature_convertor_app.presentation

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.temperature_convertor_app.events.ConvertorOperation
import com.example.temperature_convertor_app.events.ViewState
import com.example.temperature_convertor_app.presentation.ui.theme.blue
import com.example.temperature_convertor_app.presentation.ui.theme.darkBlue
import com.example.temperature_convertor_app.presentation.ui.theme.lightBlue
import listOfTemperature

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertorScreen(
    modifier: Modifier,
    onConvert:(ConvertorOperation)->Unit,
    state: ViewState
) {
    val context = LocalContext.current
    var temperature : String by remember { mutableStateOf("") }
    var expandedConvertFrom by remember { mutableStateOf(false) }
    var expandedConvertTo by remember { mutableStateOf(false) }
    val iconConvertFrom = if (expandedConvertFrom) {
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }
    val iconConvertTo = if (expandedConvertTo) {
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }
    var textFieldConvertFrom by remember {
        mutableStateOf(Size.Zero)
    }

    var textFieldConvertTo by remember {
        mutableStateOf(Size.Zero)
    }

    var resultVisible by remember {
        mutableStateOf(false)
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = "Temperature Convertor",
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = darkBlue
            ),
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Temperature: ",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = darkBlue
                )
                )

            OutlinedTextField(
                value = temperature,
                onValueChange = {temperature = it
                                state.temperature = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                maxLines = 1,
                textStyle = TextStyle(
                    color = blue,
                    fontSize = 20.sp
                )
                )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Convert From: ",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = darkBlue
                )
                )
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                OutlinedTextField(
                    value = state.convertFrom,
                    onValueChange = {state.convertFrom = it},
                    trailingIcon = {
                        Icon(iconConvertFrom,"", Modifier.clickable { expandedConvertFrom = !expandedConvertFrom })
                    },
                    enabled = false,
                    modifier = Modifier
                        .onGloballyPositioned {coordinates->
                            textFieldConvertFrom = coordinates.size.toSize()
                        }
                )
                DropdownMenu(
                    expanded = expandedConvertFrom,
                    onDismissRequest = {
                        expandedConvertFrom = false
                    },
                    modifier = Modifier
                        .width(with(LocalDensity.current){textFieldConvertFrom.width.toDp()})
                    ) {
                    listOfTemperature.forEach { label->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            state.convertFrom = label
                            expandedConvertFrom = false
                        })
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(25.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Convert To: ",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = darkBlue
                )
            )
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                OutlinedTextField(
                    value = state.convertTo,
                    onValueChange = {state.convertTo = it},
                    trailingIcon = {
                        Icon(iconConvertFrom,"",
                            Modifier.clickable { expandedConvertTo = !expandedConvertTo })
                    },
                    enabled = false,
                    modifier = Modifier
                        .onGloballyPositioned {coordinates->
                            textFieldConvertTo = coordinates.size.toSize()
                        }
                )
                DropdownMenu(
                    expanded = expandedConvertTo,
                    onDismissRequest = {
                        expandedConvertFrom = false
                    },
                    modifier = Modifier
                        .width(with(LocalDensity.current){textFieldConvertTo.width.toDp()})
                ) {
                    listOfTemperature.forEach { label->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            state.convertTo = label
                            expandedConvertTo = false
                        })
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                if(temperature.isNotBlank()){
                    if(state.convertFrom.isBlank() || state.convertTo.isBlank()){
                        Toast.makeText(context,"Select the temperature", Toast.LENGTH_SHORT).show()
                    }
                    onConvert(ConvertorOperation.Temperature(temperature.toDouble()))
                    resultVisible = true
                }else {
                    Toast.makeText(context,"Enter the temperature", Toast.LENGTH_SHORT).show()
                }
                      },
            colors = ButtonDefaults.buttonColors(
                darkBlue
            ),
            shape = RoundedCornerShape(16.dp),

            ) {
            Text(text = "Convert",
                style = TextStyle(
                    color = lightBlue,
                    fontSize = 24.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if(resultVisible){
                Text(
                    text = "Result: ",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = darkBlue
                    )
                )
            }
            Text(
                text = state.result,
                style = TextStyle(
                    fontSize = 32.sp
                )
            )
        }
    }
}