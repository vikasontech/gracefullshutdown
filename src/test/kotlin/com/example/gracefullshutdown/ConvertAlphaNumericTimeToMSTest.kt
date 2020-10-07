package com.example.gracefullshutdown

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.util.Assert
import java.time.Duration
import java.util.function.Function

class ConvertAlphaNumericTimeToMSTest {

    @Test
    fun testCorrectValues() {
        Assert.isTrue(1L == ConvertAlphaNumericTimeToMS.convert("1ms"), "Invalid value for ms")
        Assert.isTrue(1_000L == ConvertAlphaNumericTimeToMS.convert("1s"), "Invalid value for seconds")
        Assert.isTrue(60_000L == ConvertAlphaNumericTimeToMS.convert("1m"), "Invalid value for minute")
        Assert.isTrue(3_600_000L == ConvertAlphaNumericTimeToMS.convert("1h"), "Invalid value for hours")
    }

    @Test
    fun testInvalidUnit() {
        try {
            ConvertAlphaNumericTimeToMS.convert("1x")
        } catch (nfe: NumberFormatException) {
            println(nfe.message)
            Assert.isTrue("For input string: \"1x\"" == nfe.message, "Invalid message!!!")
        }
    }

}