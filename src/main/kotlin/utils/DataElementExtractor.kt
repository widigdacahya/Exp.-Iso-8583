package utils

val dataElementLengthRules = mapOf(
    2 to -2,    // Data Element 2 has fixed length of 16 characters
    3 to 6,     // Data Element 3 has fixed length of 6 characters
    4 to 12,
    5 to 12,
    6 to 12,
    7 to 10,
    8 to 8,
    9 to 8,
    10 to 8,
    11 to 6,
    12 to 6,
    13 to 4,
    14 to 4,
    15 to 4,
    16 to 4,
    17 to 4,
    18 to 4,
    19 to 4,
    20 to 4,
    21 to 3,
    22 to 3,
    23 to 3,
    24 to 3,
    25 to 2,
    26 to 3,
    27 to 3,
    28 to 8,
    29 to 8,
    30 to 8,
    31 to 8,
    32 to -2,
    33 to -2,
    34 to -2,
    35 to -2,
    36 to -2,
    37 to 12,
    38 to 6,
    39 to 2,
    40 to 3,
    41 to 16,
    42 to 15,
    43 to 40,
    44 to -2,
    45 to -2,
    46 to -3,
    47 to -3,
    48 to -3,
    49 to 3,
    50 to 3,
    51 to 3,
    52 to 16,
    53 to 16,
    54 to -3,
    55 to -3,
    56 to -3,
    57 to -3,
    58 to -3,
    59 to -3,
    60 to -3,
    61 to -3,
    62 to -3,
    63 to -3,
    64 to 16,
    65 to 1,
    66 to 1,
    67 to 2,
    68 to 3,
    69 to 3,
    70 to 3,
    71 to 4,
    72 to 4,
    73 to 6,
    74 to 10,
    75 to 10,
    76 to 10,
    77 to 10,
    78 to 10,
    79 to 10,
    80 to 10,
    81 to 10,
    82 to 12,
    83 to 12,
    84 to 12,
    85 to 12,
    86 to 16,
    87 to 16,
    88 to 16,
    89 to 16,
    90 to 42,
    91 to 1,
    92 to 2,
    93 to 5,
    94 to 7,
    95 to 42,
    96 to 16,
    97 to 16,
    98 to 25,
    99 to -2,
    100 to -2,
    101 to -2,
    102 to -2,
    103 to -2,
    104 to -3,
    105 to -3,
    106 to -3,
    107 to -3,
    108 to -3,
    109 to -3,
    110 to -3,
    111 to -3,
    112 to -3,
    113 to -3,
    114 to -3,
    115 to -3,
    116 to -3,
    117 to -3,
    118 to -3,
    119 to -3,
    120 to -3,
    121 to -3,
    122 to -3,
    123 to -3,
    124 to -3,
    125 to -3,
    126 to -3,
    127 to -3,
    128 to 16

)


fun extractDataElements(truncatedIsoMessage: String, primaryBitmapBinary: String, secondaryBitmapBinary: String, lengthRules: Map<Int, Int>): Pair<Map<Int, String>, String> {
    val dataElements = mutableMapOf<Int, String>()
    var remainingMessage = truncatedIsoMessage

    for ((dataElement, lengthRule) in lengthRules) {
        // Check if the data element is present in the primary or secondary bitmap
        if (isDataElementPresent(dataElement, primaryBitmapBinary, secondaryBitmapBinary)) {
            val dataElementValue: String
            if (lengthRule > 0) {
                // Positive length rule: Take the specified number of characters from the beginning of the remaining ISO message
                dataElementValue = remainingMessage.substring(0, lengthRule)
                remainingMessage = remainingMessage.substring(lengthRule)
            } else {
                // Negative length rule: Take the absolute value of the rule as the number of digits to consider as the length of the data element
                val lengthDigits = -lengthRule
                println("Length digit is : $lengthDigits\n")

                val lengthValue = remainingMessage.substring(0, lengthDigits).toIntOrNull() ?: 0
                println("Length value is : $lengthValue")

                // Adjust the starting index to extract the data element after the length digits
                dataElementValue = remainingMessage.substring(lengthDigits, lengthDigits + lengthValue)
                remainingMessage = remainingMessage.substring(lengthDigits + lengthValue)
            }
            dataElements[dataElement] = dataElementValue
        }
    }

    return Pair(dataElements, remainingMessage)
}



fun isDataElementPresent(dataElement: Int, primaryBitmapBinary: String, secondaryBitmapBinary: String): Boolean {
    // Check if the data element is present in the primary or secondary bitmap
    return if (dataElement in 1..64) {
        // Data elements 1-64 are represented in the primary bitmap
        primaryBitmapBinary[dataElement - 1] == '1'
    } else {
        // Data elements 65-128 are represented in the secondary bitmap
        secondaryBitmapBinary[dataElement - 65] == '1'
    }
}
