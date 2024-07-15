package com.protoseo.inputsourceautoconverter.listener

import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.KeyEvent
import com.intellij.ide.IdeEventQueue.EventDispatcher
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.protoseo.inputsourceautoconverter.checker.InputSourceChecker
import com.protoseo.inputsourceautoconverter.converter.MacInputSourceConverter
import com.protoseo.inputsourceautoconverter.setting.InputSourceSettingState

class KeyEventDispatcher(private val project: Project) : EventDispatcher {

    override fun dispatch(e: AWTEvent): Boolean {
        if (e !is KeyEvent) {
            return false
        }

        if (InputSourceChecker.check(project)) {
            convertInputSource()
        }
        if (InputSourceChecker.checkOnCapslock(project)) {
            turnOffCapslock()
        }
        return false
    }

    private fun turnOffCapslock() = Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false)

    private fun convertInputSource() {
        val defaultInputSource = service<InputSourceSettingState>().normalModeInputSource

        MacInputSourceConverter.convert(defaultInputSource)
    }
}
