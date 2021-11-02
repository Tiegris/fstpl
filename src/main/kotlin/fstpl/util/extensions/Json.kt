package fstpl.util.extensions

import com.beust.klaxon.JsonObject
import fstpl.util.FstplModelException

fun <T> List<T>.tail() = drop(1)

fun JsonObject.readName(s: String): String {
    if (s.startsWith('$')) {
        val key = s.drop(1)
        val got = this.read(key)
        return if (got is String) got else throw FstplModelException("$s is not a valid model element!")
    } else {
        return s
    }
}

fun JsonObject.read(s: String): Any? {
    val split = s.split('.')
    val ret = if (split.size >= 2) {
        (this[split.first()] as JsonObject).read(split.tail())
    } else {
        this[split.first()]
    }
    return ret
}

private fun JsonObject.read(split: List<String>): Any? {
    val ret = if (split.size >= 2) {
        (this[split.first()] as JsonObject).read(split.tail())
    } else {
        this[split.first()]
    }
    return ret
}
