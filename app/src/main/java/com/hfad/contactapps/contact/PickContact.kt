package com.hfad.contactapps.permission

import android.annotation.SuppressLint
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PickContact(private val activity: AppCompatActivity) {

    @SuppressLint("Recycle")
    fun pickContact() {
        activity.registerForActivityResult(
            ActivityResultContracts.PickContact()
        ) { contactData ->
            val c = activity.contentResolver?.query(contactData, null, null, null, null)!!
            if (c.moveToFirst()) {
                val id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val hasPhone =
                    c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhone.equals("1")) {
                    val phones = activity.contentResolver?.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null
                    )!!
                    phones.moveToFirst()
//                    val phoneNumber = phones.getString(c.getColumnIndex("data 1"))
//                    val name = phones.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                }
            }
        }
    }
}