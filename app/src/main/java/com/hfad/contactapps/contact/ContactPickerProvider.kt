package com.hfad.contactapps.contact

import android.provider.ContactsContract
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ContactPickerProvider(val activity: AppCompatActivity): ContactProvider {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Void>

    override fun register(callback: (Contact) -> Unit) {
        activityResultLauncher = activity.registerForActivityResult(
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

                    if (phones.moveToFirst()) {
                        val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                        callback(Contact(name, phoneNumber))
                    }

                    phones.close()
                }
            }

            c.close()
        }
    }

    override fun request() {
        activityResultLauncher.launch(null)
    }
}