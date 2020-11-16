package com.zeus.offernotificator

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup

class OfferSearcher {

    private val notificator = Notificator()
    private val url = "https://www.cyberpuerta.mx/Computo-Hardware/Componentes/Tarjetas-de-Video/Tarjeta-de-Video-EVGA-NVIDIA-GeForce-RTX-3080-FTW3-ULTRA-Gaming-10GB-320-bit-GDDR6X-PCI-Express-x16-4-0.html"

    fun startDaemon() = runBlocking {
        while (true) {
            val doc = Jsoup.connect(url).get()
            //val doc = Jsoup.connect("https://www.cyberpuerta.mx/Computo-Hardware/Componentes/Tarjetas-de-Video/Tarjeta-de-Video-EVGA-NVIDIA-GeForce-RTX-3090-FTW3-Ultra-Gaming-24GB-384-bit-GDDR6X-PCI-Express-x16-4-0.html").get()
            //println(doc.body())
            println("checking....")
            val button = doc.select(".cartIconPDP")
            button.forEach {
                println(it.toString())
                notificator.sendNotification("Disponible", url)
            }

            delay(60_000)
        }
    }
}