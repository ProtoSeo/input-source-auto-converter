package com.protoseo.inputsourceautoconverter.setting

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.xmlb.XmlSerializerUtil
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

@Service
@State(
    name = "com.protoseo.inputsourceautoconverter.setting",
    storages = [Storage("InputSourceAutoConverterSettingsState.xml")]
)
class InputSourceSettingState : PersistentStateComponent<InputSourceSettingState> {

    private val logger: Logger = Logger.getInstance(InputSourceSettingState::class.java)
    var insertModeInputSource: String = ""
    var notInsertModeInputSource: String = ""

    init {
        val initInputSource = InputSourceUtils.getDefaultInputSource()
        if (insertModeInputSource.isBlank()) {
            insertModeInputSource = initInputSource
        }
        if (notInsertModeInputSource.isBlank()) {
            notInsertModeInputSource = initInputSource
        }
    }

    override fun getState(): InputSourceSettingState {
        logger.info("State.getState")
        return this
    }

    override fun loadState(state: InputSourceSettingState) {
        logger.info("State.loadState")
        logger.info("" + insertModeInputSource)
        logger.info("" + notInsertModeInputSource)
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: InputSourceSettingState
            get() = service()
    }
}
