package com.zeus.offernotificator

import com.google.common.io.Resources.getResource
import java.awt.Desktop
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.net.URL

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

        iconTray.toolTip = "OfferNotificator"

        tray.add(iconTray)
    }

    fun sendNotification(message: String, url: String) {
        iconTray.displayMessage("Offerta encontrada!!!",message, TrayIcon.MessageType.INFO )
        iconTray.addActionListener {
            val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(URL(url).toURI())
            } else {
                println("Desktop not supported")
            }
        }
    }
}