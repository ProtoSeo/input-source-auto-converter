package com.protoseo.inputsourceautoconverter.service

import java.util.concurrent.ConcurrentHashMap
import com.intellij.openapi.components.Service
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.command.CommandState.Mode

@Service
class EditorVimModeService {

    private val modesByEditor: MutableMap<String, Mode> = ConcurrentHashMap()

    fun isFirstOpen(editor: Editor): Boolean {
        val key = editor.toString()
        if (!modesByEditor.containsKey(key)) {
            modesByEditor[key] = CommandState.getInstance(editor).mode
            return true
        }
        return false
    }

    // Insert -> Not Insert Mode(Command, Visual, CMD_Line, ...) = true
    fun isChangeMode(editor: Editor): Boolean {
        val key = editor.toString()
        if (!modesByEditor.containsKey(key)) {
            return false
        }
        val currentMode = CommandState.getInstance(editor).mode
        val prevMode = modesByEditor[key]
        modesByEditor[key] = currentMode
        return prevMode == Mode.INSERT && currentMode != Mode.INSERT
    }
}
