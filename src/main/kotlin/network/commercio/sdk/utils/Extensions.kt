package network.commercio.sdk.utils

import org.spongycastle.util.encoders.Hex
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tries to execute the given [block], returning its result or `null` if some exception was
 * raised.
 */
suspend fun <T> tryOrNull(block: suspend () -> T): T? {
    return try {
        block()
    } catch (t: Throwable) {
        println(t)
        null
    }
}

/**
 * Converts the given [ByteArray] into its hexadecimal representation.
 */
fun ByteArray.toHex(): String {
    return Hex.toHexString(this)
}

fun getTimeStamp(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).format(Date())
}