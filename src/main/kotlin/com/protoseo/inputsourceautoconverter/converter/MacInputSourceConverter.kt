package com.protoseo.inputsourceautoconverter.converter

import com.protoseo.inputsourceautoconverter.common.InputSource

object MacInputSourceConverter : InputSourceConverter {

    private lateinit var cachedInputSources: Map<String, InputSource>

    override fun findSelectableInputSources(): Array<InputSource> {
        if (!this::cachedInputSources.isInitialized) {
            initCachedInputSources()
        }
        return this.cachedInputSources.values.toTypedArray()
    }

    private fun initCachedInputSources() {
        val stdout = MacNative.findSelectableInputSources()
        cachedInputSources = stdout.split('\n')
            .filter { it.isNotBlank() }
            .associate {
                val t = it.split(":").toTypedArray()
                t[0] to InputSource(t[0], t[1], t[2])
            }
    }

    override fun convert(inputSource: InputSource) {
        convertInputSourceById(inputSource.id)
    }

    private fun convertInputSourceById(id: String) {
        MacNative.convert(id)
    }
}
