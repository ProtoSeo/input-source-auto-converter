package com.protoseo.inputsourceautoconverter.common

import com.intellij.util.xmlb.Converter

data class InputSource(
    val id: String,
    val name: String,
    val language: String
) : Converter<InputSource>() {

    companion object {
        fun create(): InputSource {
            return InputSource("", "", "")
        }
    }

    override fun toString(): String {
        return toString(this)
    }

    override fun toString(value: InputSource): String {
        return "${value.name} (${value.id}) [${value.language}]"
    }

    override fun fromString(value: String): InputSource {
        val values = value.split(" ").toTypedArray()
        val name = value[0].toString()
        val id = values[1].substring(1, values[1].length - 1)
        val language = values[2].substring(1, values[2].length - 1)
        return InputSource(id, name, language)
    }
}
