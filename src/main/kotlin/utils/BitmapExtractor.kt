package com.example.parser
import utils.hexCharToBinary

fun extractBitmaps(truncatedIsoMessage: String): Triple<String, String, String> {
    val bitmapHex = truncatedIsoMessage.substring(0, 16)
    val remainingTruncatedIsoMessage = truncatedIsoMessage.substring(16)

    // Convert each hexadecimal character of the primary bitmap to binary
    val BitmapBinary = bitmapHex.map { hexCharToBinary(it) }.joinToString("")



    return Triple(bitmapHex, remainingTruncatedIsoMessage, BitmapBinary)
}
