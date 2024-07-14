package com.protoseo.inputsourceautoconverter.converter

import com.protoseo.inputsourceautoconverter.common.InputSource

interface InputSourceConverter {

    fun findSelectableInputSources(): Array<InputSource>

    fun convert(inputSource: InputSource)
}
