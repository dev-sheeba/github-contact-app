package com.hfad.contactapps.permission

interface ContactPermissionController {
    fun register(callback: (isGranted: Boolean) -> Unit)
    fun request()
}
