import com.example.parser.extractBitmaps
import utils.dataElementLengthRules
import utils.extractDataElements
import utils.extractIsoHeader
import utils.extractMti

fun calculateStringLength(input: String): Int {
    return input.length
}

fun main() {
    val isoMessage = "ISO1234567890200F23E40012841800200000000040000001666666612345678901810000000000500000103195003000534025003010412120105601006000715216666661234567890=12120000000005342222110001600010250815123456   00000005000036000314109001001001"

//    val length = calculateStringLength(isoMeessage)
//    println("Length of the string: $length")


    val (isoHeader, truncatedIsoHeaderMessage) = extractIsoHeader(isoMessage)
    println("ISO Header : " + isoHeader)
    println("Sisa message : " + truncatedIsoHeaderMessage + '\n')


    val (isoMTI, truncatedMtiIso) = extractMti(truncatedIsoHeaderMessage)
    println("MTI : " + isoMTI)
    println("Sisa message : " + truncatedMtiIso + '\n')

    val (primaryBitmapHex, truncatedPPrimaryBitmap, primaryBitmapBin ) = extractBitmaps(truncatedMtiIso)
    println("Primary Bitmap (Hex): $primaryBitmapHex")
    println("Primary Bitmap (Binary): $primaryBitmapBin")
    println("Sisa message : " + truncatedPPrimaryBitmap + '\n' )

    if (primaryBitmapBin[0] == '1') {
        val (secondaryBitmapHex, truncatedSecondaryBitmap,secondaryBitmapBin) = extractBitmaps(truncatedPPrimaryBitmap)
        println("Secondary Bitmap (Hex): $secondaryBitmapHex")
        println("Secondary Bitmap (Binary): $secondaryBitmapBin")
        println("Remaining Truncated ISO Message: $truncatedSecondaryBitmap\n")

        val (dataElements, remainingTruncatedIsoMessage) = extractDataElements(truncatedSecondaryBitmap, primaryBitmapBin, secondaryBitmapBin, dataElementLengthRules)
        println("Data Elements: $dataElements")
        println("Remaining Truncated ISO Message: $remainingTruncatedIsoMessage")


    } else {
        println("No Secondary Bitmap present.")
    }



//    println("035".toInt())





}
