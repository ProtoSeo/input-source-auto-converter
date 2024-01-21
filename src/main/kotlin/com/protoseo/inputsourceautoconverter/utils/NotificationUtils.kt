package com.protoseo.inputsourceautoconverter.utils

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType.ERROR
import com.intellij.openapi.project.Project

object NotificationUtils {

    // TODO: Add suggestion action that disabled this plugin
    fun notifyError(project: Project, title: String = "InputSource Auto Converter", content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("ErrorNotificationGroup")
            .createNotification(content, ERROR)
            .setTitle(title)
            .setSuggestionType(true)
            .notify(project)
    }
}
