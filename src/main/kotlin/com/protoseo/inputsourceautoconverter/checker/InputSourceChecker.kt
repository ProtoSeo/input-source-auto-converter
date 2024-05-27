package com.protoseo.inputsourceautoconverter.checker

import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.im.InputContext
import java.util.Locale
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.command.CommandState.Mode
import com.protoseo.inputsourceautoconverter.common.InputSource
import com.protoseo.inputsourceautoconverter.service.EditorVimModeService
import com.protoseo.inputsourceautoconverter.setting.InputSourceSettingState

class InputSourceChecker {

    companion object {
        fun check(project: Project): Boolean {
            val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return false
            val isStrictMode = service<InputSourceSettingState>().strictMode
            val inputSource = service<InputSourceSettingState>().normalModeInputSource

            if (isStrictMode) {
                return isFocusCodeEditor(project) && isNotInsertMode(editor) && isNotSelectedInputSource(inputSource)
            }

            val editorVimModeService = project.service<EditorVimModeService>()
            return (editorVimModeService.isFirstOpen(editor) && isNotSelectedInputSource(inputSource)) ||
                    (editorVimModeService.isChangedVimMode(editor) && isNotSelectedInputSource(inputSource))
        }

        fun checkOnCapslock(project: Project): Boolean {
            val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return false

            return isFocusCodeEditor(project) && isNotInsertMode(editor) && Toolkit.getDefaultToolkit()
                .getLockingKeyState(KeyEvent.VK_CAPS_LOCK)
        }

        private fun isNotSelectedInputSource(inputSource: InputSource): Boolean {
            val locale = InputContext.getInstance().locale

            return inputSource.language != locale.language && Locale.US.country != locale.country
        }

        private fun isFocusCodeEditor(project: Project) =
            ToolWindowManager.getInstance(project).activeToolWindowId == null

        private fun isNotInsertMode(editor: Editor) = CommandState.getInstance(editor).mode != Mode.INSERT
    }

}
