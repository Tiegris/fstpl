package fstpl.util.extensions

import java.nio.file.Path
import kotlin.io.path.name

const val call = "_call_"

fun Path.caller(): String {
     return this.getByKeyword(call)
}

fun Path.loopKey(): String {
    return this.getByKeyword("~loop_").dropCall()
}

fun Path.ifKey(): String {
    return this.getByKeyword("~if").dropCall()
}

private fun String.dropCall(): String {
    val pos = this.indexOf(call)
    return this.dropLast(this.length - pos)
}

private fun Path.getByKeyword(keyword: String): String {
    val name = this.name
    val pos = name.indexOf(keyword)
    return name.takeLast(name.length - pos - keyword.length)
}
