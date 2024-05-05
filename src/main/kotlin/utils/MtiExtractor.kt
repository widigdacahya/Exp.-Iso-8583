package utils

fun extractMti(truncatedIsoMessage: String): Pair<String, String> {
    // Extract the first 4 characters of the truncated ISO message as the MTI
    val mti = if (truncatedIsoMessage.length >= 4) {
        truncatedIsoMessage.substring(0, 4)
    } else {
        ""
    }

    // Return the MTI and the remaining truncated ISO message
    val remainingTruncatedIsoMessage = if (truncatedIsoMessage.length > 4) {
        truncatedIsoMessage.substring(4)
    } else {
        ""
    }

    return Pair(mti, remainingTruncatedIsoMessage)
}