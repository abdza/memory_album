package com.hashalbum.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rH\'J\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000e2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000e0\rH\'J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010 \u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006!"}, d2 = {"Lcom/hashalbum/app/data/ContactDao;", "", "deleteContact", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteImageContact", "hash", "", "contactId", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllContacts", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hashalbum/app/data/Contact;", "getContactById", "getContactByName", "name", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getContactsForHash", "Lcom/hashalbum/app/data/ImageContact;", "getContactsWithCount", "Lcom/hashalbum/app/data/ContactWithCount;", "getHashesForContact", "insertContact", "contact", "(Lcom/hashalbum/app/data/Contact;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertImageContact", "imageContact", "(Lcom/hashalbum/app/data/ImageContact;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchContacts", "query", "app_debug"})
@androidx.room.Dao()
public abstract interface ContactDao {
    
    @androidx.room.Query(value = "SELECT * FROM contacts ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.Contact>> getAllContacts();
    
    @androidx.room.Query(value = "SELECT * FROM contacts WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getContactById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hashalbum.app.data.Contact> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM contacts WHERE name = :name")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getContactByName(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hashalbum.app.data.Contact> $completion);
    
    @androidx.room.Insert(onConflict = 3)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertContact(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "DELETE FROM contacts WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteContact(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM image_contacts WHERE hash = :hash")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getContactsForHash(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.ImageContact>> $completion);
    
    @androidx.room.Query(value = "SELECT hash FROM image_contacts WHERE contactId = :contactId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHashesForContact(long contactId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertImageContact(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageContact imageContact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM image_contacts WHERE hash = :hash AND contactId = :contactId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteImageContact(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, long contactId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        SELECT c.id, c.name, COUNT(ic.hash) as imageCount\n        FROM contacts c\n        LEFT JOIN image_contacts ic ON c.id = ic.contactId\n        GROUP BY c.id\n        ORDER BY c.name ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ContactWithCount>> getContactsWithCount();
    
    @androidx.room.Query(value = "SELECT * FROM contacts WHERE name LIKE \'%\' || :query || \'%\' ORDER BY name ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchContacts(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.Contact>> $completion);
}