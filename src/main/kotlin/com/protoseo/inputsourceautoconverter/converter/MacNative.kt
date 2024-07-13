package com.protoseo.inputsourceautoconverter.converter

object MacNative {

    init {
        System.loadLibrary("mac-native")
    }

    external fun findSelectableInputSources(): String

    external fun convert(inputSource: String): Boolean
}
