package com.hashalbum.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0017J\u0012\u0010\u0019\u001a\u00020\u00172\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0017H\u0002J\b\u0010\u001d\u001a\u00020\u0017H\u0002J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\bH\u0002J\b\u0010 \u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/hashalbum/app/ui/ContactsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/hashalbum/app/databinding/ActivityContactsBinding;", "contactsAdapter", "Lcom/hashalbum/app/ui/ContactsAdapter;", "currentContact", "Lcom/hashalbum/app/data/ContactWithCount;", "galleryAdapter", "Lcom/hashalbum/app/ui/GalleryAdapter;", "repository", "Lcom/hashalbum/app/data/ImageRepository;", "getDateModified", "", "uri", "Landroid/net/Uri;", "groupImagesByDate", "", "Lcom/hashalbum/app/data/GalleryItem;", "images", "Lcom/hashalbum/app/data/GalleryImage;", "loadContacts", "", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupContactsList", "setupToolbar", "showContactImages", "contact", "showContactsList", "app_debug"})
public final class ContactsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.hashalbum.app.databinding.ActivityContactsBinding binding;
    private com.hashalbum.app.ui.ContactsAdapter contactsAdapter;
    private com.hashalbum.app.ui.GalleryAdapter galleryAdapter;
    private com.hashalbum.app.data.ImageRepository repository;
    @org.jetbrains.annotations.Nullable()
    private com.hashalbum.app.data.ContactWithCount currentContact;
    
    public ContactsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupContactsList() {
    }
    
    private final void loadContacts() {
    }
    
    private final void showContactImages(com.hashalbum.app.data.ContactWithCount contact) {
    }
    
    private final void showContactsList() {
    }
    
    private final long getDateModified(android.net.Uri uri) {
        return 0L;
    }
    
    private final java.util.List<com.hashalbum.app.data.GalleryItem> groupImagesByDate(java.util.List<com.hashalbum.app.data.GalleryImage> images) {
        return null;
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    public void onBackPressed() {
    }
}