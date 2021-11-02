class ${name} {
<#list endpoints as e>
    @${e.type}
    fun ${e.name}() {

    }
</#list>
}
