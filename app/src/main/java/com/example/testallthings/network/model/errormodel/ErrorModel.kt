package com.example.testallthings.network.model.errormodel

data class ErrorModel(
    val status:String,
    val message:String,
    val error:Map<String,List<String>>?=null
)
