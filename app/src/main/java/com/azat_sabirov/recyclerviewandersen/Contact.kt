package com.azat_sabirov.recyclerviewandersen

import java.io.Serializable


data class Contact(
    var id: Int,
    var name: String,
    var surname: String,
    var phoneNumber: String,
    var image: String
) : Serializable
