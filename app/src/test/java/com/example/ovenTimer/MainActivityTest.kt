package com.example.ovenTimer

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MainActivityKtTest {
    lateinit var target: MainActivity
    @Before
    fun setup(){
        target = MainActivity()
    }

    @Test
    fun convertWattsToInt_givenString_returnsInt() {
        val actual = target.convertWattsToInt("500W")
        assertEquals(actual,500)
    }

    @Test
    fun calcWarmingTime_test() {
        assertEquals(target.calcWarmingTime(0,10,100,100),10)
        assertEquals(target.calcWarmingTime(1,10,100,100),70)
        assertEquals(target.calcWarmingTime(1,0,100,200),30)
        assertEquals(target.calcWarmingTime(1,0,200,100),120)
    }
}
