package com.protoseo.inputsourceautoconverter.setting

import javax.swing.JPanel
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

class InputSourceSettingComponent {

    private val state: InputSourceSettingState = InputSourceSettingState.instance
    val insertModeInputSourceComboBox = ComboBox(InputSourceUtils.getInputSources())
    val notInsertModeInputSourceComboBox = ComboBox(InputSourceUtils.getInputSources())

    val mainPanel: JPanel by lazy {
        FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Insert mode input source"), insertModeInputSourceComboBox, 1)
            .addLabeledComponent(JBLabel("Not Insert mode input source"), notInsertModeInputSourceComboBox, 2)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    init {
        insertModeInputSourceComboBox.selectedItem = state.insertModeInputSource
        notInsertModeInputSourceComboBox.selectedItem = state.notInsertModeInputSource
    }
}
