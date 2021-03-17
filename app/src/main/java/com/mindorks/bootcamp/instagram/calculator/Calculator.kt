package com.mindorks.bootcamp.instagram.calculator

class Calculator(val operation: Operation) {

    fun add(a:Int,b:Int):Int {
        return operation.add(a,b)
    }

    fun multiply(a:Int,b: Int):Int{
        return operation.multiply(a,b)
    }
}