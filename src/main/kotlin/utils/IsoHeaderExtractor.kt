package utils

fun extractIsoHeader(isoMessage: String): Pair<String, String> {
    // Check if the ISO message is at least 12 characters long
    if (isoMessage.length >= 12) {
        // Extract the ISO header (first 12 characters)
        val isoHeader = isoMessage.substring(0, 12)

        // Truncate the ISO message after the ISO header
        val truncatedIsoMessage = isoMessage.substring(12)

        return Pair(isoHeader, truncatedIsoMessage)
    } else {
        // If the ISO message is shorter than 12 characters, return the entire message
        return Pair(isoMessage, "")
    }
}