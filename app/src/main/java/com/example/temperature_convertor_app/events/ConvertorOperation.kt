package com.example.temperature_convertor_app.events

import android.health.connect.datatypes.units.Temperature

sealed class ConvertorOperation{
    class Temperature(var temperature: Double): ConvertorOperation()
    //object Result: ConvertorOperation()
}
