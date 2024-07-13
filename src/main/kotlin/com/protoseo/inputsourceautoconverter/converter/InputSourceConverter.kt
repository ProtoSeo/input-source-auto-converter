package com.protoseo.inputsourceautoconverter.converter

interface InputSourceConverter {

    fun findSelectableInputSources(): String

    fun convert(inputSource: String)
}
