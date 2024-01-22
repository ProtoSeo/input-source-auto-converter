package com.protoseo.inputsourceautoconverter.setting

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import com.protoseo.inputsourceautoconverter.common.InputSource
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

@Service
@State(
    name = "com.protoseo.inputsourceautoconverter.setting",
    storages = [Storage("InputSourceAutoConverterSettingsState.xml")]
)
class InputSourceSettingState : PersistentStateComponent<InputSourceSettingState> {

    var projectInitInputSource: InputSource = InputSource.create()
    var normalModeInputSource: InputSource = InputSource.create()
    var strictMode: Boolean = false

    init {
        val initInputSource = InputSourceUtils.getDefaultInputSource()
        if (projectInitInputSource == InputSource.create()) {
            projectInitInputSource = initInputSource
        }
        if (normalModeInputSource == InputSource.create()) {
            normalModeInputSource = initInputSource
        }
    }

    override fun getState(): InputSourceSettingState {
        return this
    }

    override fun loadState(state: InputSourceSettingState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: InputSourceSettingState
            get() = service()
    }
}
