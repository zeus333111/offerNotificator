package com.zeus.offernotificator

import com.google.common.io.Resources.getResource
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon

class Notificator {

    private lateinit var iconTray: TrayIcon

    init {
        initNotificator()
    }

    private fun initNotificator() {
        val tray = SystemTray.getSystemTray()

        val image = Toolkit.getDefaultToolkit().createImage(getResource("notification_icon.png"))

        iconTray = TrayIcon(image, "Tray demo")
        iconTray.isImageAutoSize = true

        iconTray.toolTip = "System tray demo"

        tray.add(iconTray)
    }

    fun sendNotification(message: String) {
        iconTray.displayMessage("Offerta encontrada!!!",message, TrayIcon.MessageType.INFO )
    }
}