package com.hashalbum.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getContactById(id: Long): Contact?

    @Query("SELECT * FROM contacts WHERE name = :name")
    suspend fun getContactByName(name: String): Contact?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertContact(contact: Contact): Long

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContact(id: Long)

    @Query("SELECT * FROM image_contacts WHERE hash = :hash")
    suspend fun getContactsForHash(hash: String): List<ImageContact>

    @Query("SELECT hash FROM image_contacts WHERE contactId = :contactId")
    suspend fun getHashesForContact(contactId: Long): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImageContact(imageContact: ImageContact)

    @Query("DELETE FROM image_contacts WHERE hash = :hash AND contactId = :contactId")
    suspend fun deleteImageContact(hash: String, contactId: Long)

    @Query("""
        SELECT c.id, c.name, COUNT(ic.hash) as imageCount
        FROM contacts c
        INNER JOIN image_contacts ic ON c.id = ic.contactId
        GROUP BY c.id
        HAVING imageCount > 0
        ORDER BY c.name ASC
    """)
    fun getContactsWithCount(): Flow<List<ContactWithCount>>

    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun searchContacts(query: String): List<Contact>
}
