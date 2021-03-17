package com.mindorks.bootcamp.instagram.calculator

import com.mindorks.bootcamp.instagram.calculator.Calculator
import com.mindorks.bootcamp.instagram.calculator.Operation
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculatorTest {

    @Mock
    lateinit var operation: Operation

    lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator(operation)
    }

    @Test
    fun givenValidInput_whenAdd_shouldCallAddOperation() {
        val a = 4
        val b = 2
        calculator.add(a,b)
        verify(operation).add(a,b)
        verify(operation, never()).multiply(a,b)
    }
}