package fstpl

import com.beust.klaxon.JsonObject
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.Parser.Companion.default
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import fstpl.fstpl.TemplateResolver
import fstpl.util.FstplException
import fstpl.util.extensions.isEmpty
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.pathString
import kotlin.system.exitProcess


class Fstpl : CliktCommand() {
    private val model by option("-m", "--model", help = "Path to your json model.").required()
    private val outputRoot by option("-o", "--output", help = "Output directory.")
    private val verbose by option("-v", "--verbose", help = "Print stack trace").flag(default = false)
    private val tplRoot by argument(help = "Path of the template directory.")

    override fun run() {
        val tplRoot: Path = Paths.get(tplRoot)
        if (!tplRoot.isDirectory()) {
            println("Template directory: $tplRoot is not an existing directory!")
            exitProcess(1)
        }
        val model: JsonObject =
            try {
                default().parse(model) as JsonObject
            } catch (e: KlaxonException) {
                println("Can't parse json: ${e.message}!")
                if (verbose) {
                    e.printStackTrace()
                }
                exitProcess(1)
            }
        val outRoot: Path = Paths.get(outputRoot ?: "${tplRoot.parent.pathString}/output")
        if (!outRoot.parent.exists()) {
            println("Output directory parent does not exist!")
            exitProcess(1)
        }
        if (outRoot.exists() && !outRoot.isDirectory()) {
            println("Output directory is not a directory!")
            exitProcess(1)
        }
        if (outRoot.exists() && !outRoot.isEmpty()) {
            println("Output directory is not empty")
            exitProcess(1)
        }
        try {
            TemplateResolver.resolve(model, tplRoot, outRoot)
        } catch (e: FstplException) {
            println(e.message)
            if (verbose) {
                e.printStackTrace()
            }
            exitProcess(1)
        }

    }
}

fun main(args: Array<String>) = Fstpl().main(args)
