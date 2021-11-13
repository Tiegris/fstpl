package fstpl.util.extensions

import com.beust.klaxon.JsonObject
import fstpl.util.FstplModelException

fun <T> List<T>.tail() = drop(1)

fun JsonObject.readName(s: String): String {
    if (s.contains('$')) {
        val (pre, key, post) = s.separate()
        val got = this.read(key)
        return if (got is String) "$pre$got$post" else throw FstplModelException("$s is not a valid model element!")
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

private fun String.separate(): Triple<String, String, String> {
    if (this.startsWith('$') && !this.contains('{'))
        return Triple("", this.drop(1), "")
    else {
        val pre: String
        val key: String
        val post: String

        val posDol = this.indexOf("$")
        val posOpen = this.indexOf("{")
        val posClose = this.indexOf("}")
        pre = this.take(posDol)

        key = this.drop(posOpen + 1).dropLast(length - posOpen - posClose + 1)

        post = this.takeLast(this.length - posClose - 1)

        return Triple(pre, key, post)
    }
}

private fun JsonObject.read(split: List<String>): Any? {
    val ret = if (split.size >= 2) {
        (this[split.first()] as JsonObject).read(split.tail())
    } else {
        this[split.first()]
    }
    return ret
}
