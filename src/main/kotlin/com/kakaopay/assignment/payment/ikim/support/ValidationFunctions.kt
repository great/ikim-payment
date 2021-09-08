package com.kakaopay.assignment.payment.ikim.support

private val alphaNumeric = Regex("[A-Za-z0-9]+")
private val numeric = Regex("[0-9]+")

fun String.isAlphaNumeric() = alphaNumeric.matches(this)
fun String.isNumeric() = numeric.matches(this)
