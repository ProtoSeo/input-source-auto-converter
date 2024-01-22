package com.protoseo.inputsourceautoconverter.setting

import javax.swing.JComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.protoseo.inputsourceautoconverter.common.InputSource

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
        val result = (component.projectInitInputSourceComboBox.selectedItem != state.projectInitInputSource
                || component.normalModeInputSourceComboBox.selectedItem != state.normalModeInputSource
                || component.strictModeCheckBox.isSelected != state.strictMode)
        logger.info("Configurable.isModified : $result")
        return result
    }

    override fun apply() {
        logger.info("Configurable.apply")
        val state = InputSourceSettingState.instance
        this.state.projectInitInputSource = component.projectInitInputSourceComboBox.selectedItem()
        this.state.normalModeInputSource = component.normalModeInputSourceComboBox.selectedItem()
        this.state.strictMode = component.strictModeCheckBox.isSelected
        state.loadState(this.state)
    }

    override fun reset() {
        logger.info("configurable.reset")
        val state = InputSourceSettingState.instance
        component.projectInitInputSourceComboBox.selectedItem = state.projectInitInputSource
        component.normalModeInputSourceComboBox.selectedItem = state.normalModeInputSource
        component.strictModeCheckBox.isSelected = state.strictMode
    }
}

fun ComboBox<InputSource>.selectedItem(): InputSource {
    return this.selectedItem as InputSource
}
