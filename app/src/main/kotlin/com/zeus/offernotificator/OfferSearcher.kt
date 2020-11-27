package com.zeus.offernotificator

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup

class OfferSearcher {

    private val notificator = Notificator()
    //private val url = "https://www.cyberpuerta.mx/Computo-Hardware/Componentes/Tarjetas-de-Video/Tarjeta-de-Video-EVGA-NVIDIA-GeForce-RTX-3080-FTW3-ULTRA-Gaming-10GB-320-bit-GDDR6X-PCI-Express-x16-4-0.html"
    private val url = "https://www.cyberpuerta.mx/Tarjetas-de-Video-NVIDIA-RTX-Serie-30/"
    //private val url = "https://www.cyberpuerta.mx/NVIDIA-GeForce-RTX-2060/"

    fun startDaemon() = runBlocking {
        while (true) {
            val doc = Jsoup.connect(url).get()
            println("checking....")
            val button = doc.select(".submitButton")
            button.forEach {
                //println(it.toString())
                if (it.text().contains("Añadir al carrito")) {
                    notificator.sendNotification("Disponible", url)
                    println("un multiple")
                }
            }

            delay(60_000)
        }
    }
}