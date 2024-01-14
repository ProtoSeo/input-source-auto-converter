package com.protoseo.inputsourceautoconverter.setting

import javax.swing.JComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

class InputSourceSettingConfigurable : Configurable {

    private val logger: Logger = Logger.getInstance(InputSourceSettingConfigurable::class.java)
    private lateinit var component: InputSourceSettingComponent
    private lateinit var state: InputSourceSettingState

    override fun getDisplayName(): String = "Input Source Auto Converter"

    override fun createComponent(): JComponent {
        component = InputSourceSettingComponent()
        state = InputSourceSettingState.instance
        return component.mainPanel
    }

    override fun isModified(): Boolean {
        val state = InputSourceSettingState.instance
        val result = (component.insertModeInputSourceComboBox.selectedItem != state.insertModeInputSource
                || component.notInsertModeInputSourceComboBox.selectedItem != state.notInsertModeInputSource)
        logger.info("Configurable.isModified : $result")
        return result
    }

    override fun apply() {
        logger.info("Configurable.apply")
        val state = InputSourceSettingState.instance
        this.state.insertModeInputSource =  component.insertModeInputSourceComboBox.selectedItem()
        this.state.notInsertModeInputSource = component.notInsertModeInputSourceComboBox.selectedItem()
        state.loadState(this.state)
    }

    override fun reset() {
        logger.info("configurable.reset")
        val state = InputSourceSettingState.instance
        component.insertModeInputSourceComboBox.selectedItem = state.insertModeInputSource
        component.notInsertModeInputSourceComboBox.selectedItem = state.notInsertModeInputSource
    }
}

fun ComboBox<String?>.selectedItem(): String {
    return this.selectedItem?.toString() ?: InputSourceUtils.getDefaultInputSource()
}
