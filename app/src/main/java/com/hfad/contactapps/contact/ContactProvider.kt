package com.hfad.contactapps.contact

interface ContactProvider {
    fun register(callback: (Contact) -> Unit)
    fun request()
}