package com.example.discordclone.data

import com.example.discordclone.model.Chat
import com.example.discordclone.model.Friends
import com.example.discordclone.model.Server

class DataDiscord {
    fun insertDataOfServer(): List<Server> {
        val servers = mutableListOf<Server>()

        val server01 = Server(
            "Server 01",
            1
        )

        val server02 = Server(
            "Server 02",
            2
        )

        val server03 = Server(
            "Server 03",
            3
        )

        val server04 = Server(
            "Server 04",
            4
        )

        val server05 = Server(
            "Server 05",
            5
        )
        val server06 = Server(
            "Server 06",
            6
        )
        val server07 = Server(
            "Server 07",
            7
        )

        servers.add(server01)
        servers.add(server02)
        servers.add(server03)
        servers.add(server04)
        servers.add(server05)
        servers.add(server06)
        servers.add(server07)

        return servers
    }

    fun insertDataOfFriends(): List<Friends> {
        val friends = mutableListOf<Friends>()

        val friend01 = Friends(
            "Ginno",
            1,
            true
        )

        val friend02 = Friends(
            "Karla",
            2,
            true
        )

        val friend03 = Friends(
            "Fausto",
            3,
            false
        )

        val friend04 = Friends(
            "Lizbeth",
            4,
            true
        )

        val friend05 = Friends(
            "Santiago",
            5,
            false
        )

        friends.add(friend01)
        friends.add(friend02)
        friends.add(friend03)
        friends.add(friend04)
        friends.add(friend05)

        return friends
    }

    fun insertDataOfChat(): List<Chat> {
        val chats = mutableListOf<Chat>()

        val chat01 = Chat(
            "Santiago Leon",
            "¡Hola! ¿Has escuchado sobre la nueva generación de iPhones?"
        )

        val chat02 = Chat(
            "Fausto Roman",
            "¡Hola! Sí, por supuesto. La última generación de iPhones ha introducido " +
                    "mejoras en el rendimiento y la cámara, así como nuevas funciones de software.."
        )

        val chat03 = Chat(
            "Santiago Leon",
            "Eso suena genial. Estoy buscando comprar una nueva laptop, ¿cuáles son las " +
                    "marcas más recomendadas en este momento?"
        )

        val chat04 = Chat(
            "Fausto Roman",
            "Hay varias opciones populares, como Dell, HP, Lenovo y Apple. Depende de " +
                    "tus necesidades y presupuesto."
        )

        val chat05 = Chat(
            "Santiago Leon",
            "Estoy interesado en la inteligencia artificial. ¿Puedes explicarme " +
                    "brevemente cómo funciona?"
        )

        val chat06 = Chat(
            "Fausto Roman",
            "Claro, la inteligencia artificial implica crear programas y sistemas " +
                    "que pueden realizar tareas que normalmente requieren inteligencia humana, " +
                    "como el aprendizaje automático y el procesamiento de lenguaje natural."
        )

        val chat07 = Chat(
            "Santiago Leon",
            "¡Increíble! ¿Cuál es la diferencia entre 4G y 5G en las redes móviles?"
        )

        val chat08 = Chat(
            "Fausto Roman",
            "El 5G es la última generación de redes móviles y ofrece velocidades de " +
                    "internet mucho más rápidas que el 4G, además de una menor latencia, lo que " +
                    "es fundamental para impulsar la tecnología de vehículos autónomos y otras " +
                    "aplicaciones avanzadas."
        )

        val chat09 = Chat(
            "Santiago Leon",
            "He oído hablar mucho sobre blockchain. ¿En qué consiste?"
        )

        val chat10 = Chat(
            "Fausto Roman",
            "El blockchain es una tecnología de registro digital que registra " +
                    "transacciones de manera segura y transparente. Es la base de las " +
                    "criptomonedas como Bitcoin y tiene aplicaciones en la gestión de la cadena " +
                    "de suministro y la autenticación de datos."
        )

        chats.add(chat01)
        chats.add(chat02)
        chats.add(chat03)
        chats.add(chat04)
        chats.add(chat05)
        chats.add(chat06)
        chats.add(chat07)
        chats.add(chat08)
        chats.add(chat09)
        chats.add(chat10)

        return chats
    }
}