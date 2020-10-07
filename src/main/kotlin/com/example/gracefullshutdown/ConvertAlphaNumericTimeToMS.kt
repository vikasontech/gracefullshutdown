package com.example.gracefullshutdown

import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.function.Function

object ConvertAlphaNumericTimeToMS {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val numberFormatError = Function<String, Exception> { NumberFormatException(it) }
    private val formatErrorMsg = Function<String, String> { "For input string: \"$it\"" }
    private const val MILLISECOND: String = "ms"
    private const val SECOND: String = "s"
    private const val MINUTE: String = "m"
    private const val HOUR: String = "h"

    fun convert(value: String): Long {

        return try {
            when (getUnitForMillis(value)) {
                MILLISECOND -> getTimeValue(value, 2)
                else -> checkIfUnitIsHourOrMinOrSecond(value)
            }
        } catch (nfe: NumberFormatException) {
            log.error("Invalid value for number field {}", value)
            throwError(numberFormatError, formatErrorMsg.apply(value))
        }
    }

    private fun getUnitForMillis(value: String) = value.substring(value.length - 2)

    private fun checkIfUnitIsHourOrMinOrSecond(unit: String): Long {

        val timeValue = getTimeValue(unit, 1)

        return when (getTimeUnit(unit)) {
            HOUR -> Duration.ofHours(timeValue).toMillis()
            MINUTE -> Duration.ofMinutes(timeValue).toMillis()
            SECOND -> Duration.ofSeconds(timeValue).toMillis()
            else -> throwError(numberFormatError, formatErrorMsg.apply(unit))
        }
    }

    private fun getTimeUnit(unit: String) = unit.substring(unit.length - 1)

    private fun getTimeValue(unit: String, offset: Int) = unit.subSequence(0, unit.length - offset).toString().toLong()

    private fun throwError(exceptionFunction: java.util.function.Function<String, Exception>, value: String): Nothing = throw exceptionFunction.apply(value)


}