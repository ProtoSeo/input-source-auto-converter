package com.protoseo.inputsourceautoconverter.setting

import javax.swing.JPanel
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.CheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

class InputSourceSettingComponent {

    private val defaultInputSourcesComboBox = ComboBox(InputSourceUtils.getInputSources(), 450)
    val projectInitInputSourceComboBox = ComboBox(InputSourceUtils.getEnglishInputSources(), 450)
    val normalModeInputSourceComboBox = ComboBox(InputSourceUtils.getEnglishInputSources(), 450)
    val strictModeCheckBox = CheckBox("")
    val mainPanel: JPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(
            createJBLabelWithToolTipText("Project Init Input Source", "input source selected when Project initialized"),
            projectInitInputSourceComboBox
        )
        .addLabeledComponent(
            createJBLabelWithToolTipText(
                "Normal Mode(Non Insert Mode)",
                "input sources selected in Non Insert Mode(COMMAND, VISUAL, SELECT, REPLACE, CMD_LINE)"
            ),
            normalModeInputSourceComboBox
        )
        .addLabeledComponent(
            createJBLabelWithToolTipText(
                "Auto Convert Strict Check Mode",
                "Can't change input source in non insert mode"
            ),
            strictModeCheckBox
        )
        .addSeparator()
        .addLabeledComponent(JBLabel("Keyboard input sources(Read Only)"), defaultInputSourcesComboBox)
        .addComponentFillVertically(JPanel(), 0)
        .panel

    init {
        val state = ApplicationManager.getApplication().getService(InputSourceSettingState::class.java)
        projectInitInputSourceComboBox.selectedItem = state.projectInitInputSource
        normalModeInputSourceComboBox.selectedItem = state.normalModeInputSource
    }

    private fun createJBLabelWithToolTipText(text: String, toolTipText: String) = JBLabel().also {
        it.text = text
        it.toolTipText = toolTipText
    }
}
