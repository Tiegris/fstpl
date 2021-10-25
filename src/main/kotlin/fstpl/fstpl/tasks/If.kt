package fstpl.fstpl.tasks

import com.beust.klaxon.JsonObject
import fstpl.fstpl.TemplateResolver
import fstpl.util.FstplException
import fstpl.util.FstplModelException
import fstpl.util.FstplTemplateSyntaxException
import fstpl.util.read
import java.nio.file.Path
import kotlin.io.path.name
import kotlin.properties.Delegates

class If(model: JsonObject, file: Path, outPath: Path) : Task(model, file, outPath) {

    var satisfies by Delegates.notNull<Boolean>()

    override fun resolveSelf() {
        // ~if_CONDITION_call_NAME
        val parts = file.name.split('_')
        if (parts.size != 4 || parts[2] != "call") {
            throw FstplTemplateSyntaxException("Cant parse file system template IF in ${file.name}")
        }
        val conditionKey = parts[1]
        satisfies = model.read(conditionKey) as? Boolean ?: throw FstplModelException("Model element not found or not a bool value.")
        val call = parts[3]
        if (satisfies) {

        }
    }


}