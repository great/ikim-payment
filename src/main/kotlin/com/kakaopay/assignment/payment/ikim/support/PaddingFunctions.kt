package com.kakaopay.assignment.payment.ikim.support

fun Number.leftPaddingWithBlank(size: Int): String {
    val x = this.toString()
    val padding = size - x.length
    return " ".repeat(padding) + x
}

fun Number.leftPaddingWithZero(size: Int): String {
    val x = this.toString()
    val padding = size - x.length
    return "0".repeat(padding) + x
}

fun Number.rightPaddingWithBlank(size: Int): String {
    val x = this.toString()
    val padding = size - x.length
    return x + " ".repeat(padding)
}

fun String.rightPaddingWithBlank(size: Int): String {
    return this + " ".repeat(size - this.length)
}
