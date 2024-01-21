package com.protoseo.inputsourceautoconverter

import com.intellij.ide.IdeEventQueue
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.ProjectActivity
import com.jgoodies.common.base.SystemUtils
import com.maddyhome.idea.vim.VimPlugin
import com.protoseo.inputsourceautoconverter.listener.KeyEventDispatcher
import com.protoseo.inputsourceautoconverter.setting.InputSourceSettingState
import com.protoseo.inputsourceautoconverter.utils.InputSourceUtils
import com.protoseo.inputsourceautoconverter.utils.NotificationUtils

class ProjectPostStartUpActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        if (!VimPlugin.isEnabled()) {
            NotificationUtils.notifyError(project, content = "IdeaVim plugin is not installed.")
            return
        }
        if (!SystemUtils.IS_OS_MAC) {
            NotificationUtils.notifyError(project, content = "This plugin only supported mac os.")
            return
        }

        if (project.isInitialized) {
            InputSourceUtils.convertInputSource(InputSourceSettingState.instance.ideInitInputSource)
        }
        IdeEventQueue.getInstance().addDispatcher(KeyEventDispatcher(project), createDisposableIfProjectClosed(project))
    }

    private fun createDisposableIfProjectClosed(project: Project): Disposable {
        val connection = project.messageBus.connect()
        connection.subscribe(ProjectManager.TOPIC, object : ProjectManagerListener {
            override fun projectClosed(project: Project) {
                connection.dispose()
            }
        })
        return connection
    }
}
