package com.example.temperature_convertor_app.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.temperature_convertor_app.events.ConvertorOperation
import com.example.temperature_convertor_app.events.ViewState
import kotlin.math.roundToLong

class TemperatureViewModel :ViewModel() {
    var state by mutableStateOf(ViewState())
        private set

    fun onConvert(convert: ConvertorOperation){
        when(convert){
            is ConvertorOperation.Temperature -> calculateTemp(convert.temperature)

        }
    }


    private fun calculateTemp(temperature: Double) {
        val convertFrom = state.convertFrom
        val convertTo = state.convertTo
        if(convertFrom.isBlank() || convertTo.isBlank()){
            return
        }
        var result : Double = if(convertFrom == "Celsius" && convertTo == "Fahrenheit"){
            ((temperature * (9.00/5.00)) + 32.00)
        } else if (convertFrom == "Celsius" && convertTo == "Kelvin") {
            (temperature + 273.15)

        } else if (convertFrom == "Fahrenheit" && convertTo == "Celsius") {
            (temperature - 32.00) * (5.00/9.00)
        } else if (convertFrom == "Fahrenheit" && convertTo == "Kelvin") {
            ((temperature - 32.00) * (5.00/9.00)) + 273.15
        } else if (convertFrom == "Kelvin" && convertTo == "Celsius") {
            (temperature - 273.15)
        } else {
            ((temperature - 273.15) + 32.00) * (9.00/5.00)
        }
        result = Math.round(result*1000.00)/1000.00

        state = state.copy(
            temperature = temperature.toString(),
            convertFrom = convertFrom,
            convertTo = convertTo,
            result = result.toString()
        )
    }


}