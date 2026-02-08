package com.hashalbum.app.util

import android.content.Context
import android.provider.ContactsContract

object PhoneContactsHelper {

    fun getPhoneContacts(context: Context): List<String> {
        val names = mutableSetOf<String>()
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"

        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                if (!name.isNullOrBlank()) {
                    names.add(name)
                }
            }
        }

        return names.sorted()
    }
}
