package com.example.temperature_convertor_app.events

data class ViewState(
    var temperature : String = "",
    var convertFrom: String = "",
    var convertTo: String = "",
    val result:String = ""
)
