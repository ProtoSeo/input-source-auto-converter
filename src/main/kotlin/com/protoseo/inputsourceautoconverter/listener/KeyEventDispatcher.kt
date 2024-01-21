package com.protoseo.inputsourceautoconverter.listener

import java.awt.AWTEvent
import java.awt.event.KeyEvent
import java.awt.im.InputContext
import java.util.Locale
import com.intellij.ide.IdeEventQueue.EventDispatcher
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
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils

class KeyEventDispatcher(private val project: Project) : EventDispatcher {

    override fun dispatch(e: AWTEvent): Boolean {
        if (e !is KeyEvent) {
            return false
        }
        val editor = FileEditorManager.getInstance(project).selectedTextEditor
        val editorVimModeService = project.service<EditorVimModeService>()
        val inputSourceSettingState = service<InputSourceSettingState>()

        if (editor != null) {
            val targetInputSource = inputSourceSettingState.normalModeInputSource
            if (editorVimModeService.isFirstOpen(editor) && isNotSelectedInputSource(targetInputSource)) {
                InputSourceUtils.convertInputSource(targetInputSource)
            } else if (editorVimModeService.isChangedVimMode(editor) && isNotSelectedInputSource(targetInputSource)) {
                InputSourceUtils.convertInputSource(targetInputSource)
            }
            if (inputSourceSettingState.strictMode) {
                strictCheck(project, editor, targetInputSource)
            }
        }
        return false
    }

    private fun strictCheck(project: Project, editor: Editor, inputSource: InputSource) {
        if (isFocusCodeEditor(project) && isNotInsertMode(editor) && isNotSelectedInputSource(inputSource)) {
            InputSourceUtils.convertInputSource(inputSource)
        }
    }

    private fun isNotSelectedInputSource(inputSource: InputSource): Boolean {
        val locale = InputContext.getInstance().locale

        return inputSource.language != locale.language && Locale.US.country != locale.country
    }

    private fun isFocusCodeEditor(project: Project) = ToolWindowManager.getInstance(project).activeToolWindowId == null

    private fun isNotInsertMode(editor: Editor) = CommandState.getInstance(editor).mode != Mode.INSERT

}
