package com.zeus.offernotificator

import com.google.common.io.Resources.getResource
import org.gnome.gtk.Button
import org.gnome.gtk.Gtk
import org.gnome.notify.Notification
import org.gnome.notify.Notify
import java.awt.Desktop
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.net.URL

class Notificator {

    private lateinit var iconTray: TrayIcon
    private lateinit var os: OsName
    private lateinit var button: Button

    init {
        initNotificator()
    }

    private fun initNotificator() {
        os = getOsName()

        when (os) {
            is OsName.WindowsOS -> {
                val tray = SystemTray.getSystemTray()

                val image = Toolkit.getDefaultToolkit().createImage(getResource("notification_icon.png"))

                iconTray = TrayIcon(image, "Offer notificator")
                iconTray.isImageAutoSize = true

                iconTray.toolTip = "OfferNotificator"

                tray.add(iconTray)
            }
            is OsName.LinuxOS -> {
                Gtk.init(emptyArray())
                Notify.init("Offer notificator")
            }
            else -> {}
        }
    }

    fun sendNotification(message: String, url: String) {
        when(os) {
            is OsName.WindowsOS -> {
                iconTray.displayMessage("Oferta encontrada!!!", message, TrayIcon.MessageType.INFO)
                iconTray.addActionListener {
                    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
                    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(URL(url).toURI())
                    } else {
                        println("Desktop not supported")
                    }
                }
            }
            is OsName.LinuxOS -> {
                val notification = Notification("Oferta encontrada!!!!", message, "dialog-information")

                notification.addAction("action_click","Abrir") { _, _ ->
                    println("akjdshgyfaijksdhfgaksjdhfgaskjdhfgaskdjh")
                    Runtime.getRuntime().exec("xdg-open $url")
                }
                notification.show()
            }
        }
    }

    private fun getOsName(): OsName {
        val os = System.getProperty("os.name").toLowerCase()

        return when {
            os.contains("linux") -> {
                OsName.LinuxOS(os)
            }
            os.contains("windows") -> {
                OsName.WindowsOS(os)
            }
            else -> {
                OsName.NotSupported
            }
        }
    }

    sealed class OsName {
        data class WindowsOS(val name: String) : OsName()
        data class LinuxOS(val name: String) : OsName()
        object NotSupported : OsName()
    }
}