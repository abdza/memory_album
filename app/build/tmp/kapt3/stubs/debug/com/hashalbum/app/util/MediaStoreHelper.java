package com.hashalbum.app.util;

/**
 * Helper class to load images and videos from device storage using MediaStore.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\bJ$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/hashalbum/app/util/MediaStoreHelper;", "", "()V", "getImageBuckets", "", "Lcom/hashalbum/app/util/ImageBucket;", "context", "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadAllImages", "Lcom/hashalbum/app/data/GalleryImage;", "loadAllMedia", "loadAllVideos", "loadImagesFromBucket", "bucketId", "", "(Landroid/content/Context;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MediaStoreHelper {
    @org.jetbrains.annotations.NotNull()
    public static final com.hashalbum.app.util.MediaStoreHelper INSTANCE = null;
    
    private MediaStoreHelper() {
        super();
    }
    
    /**
     * Load all images from the device.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadAllImages(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.GalleryImage>> $completion) {
        return null;
    }
    
    /**
     * Load all videos from the device.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadAllVideos(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.GalleryImage>> $completion) {
        return null;
    }
    
    /**
     * Load all media (images + videos) merged and sorted by dateModified DESC.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadAllMedia(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.GalleryImage>> $completion) {
        return null;
    }
    
    /**
     * Load images from a specific folder/bucket.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadImagesFromBucket(@org.jetbrains.annotations.NotNull()
    android.content.Context context, long bucketId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.GalleryImage>> $completion) {
        return null;
    }
    
    /**
     * Get all media buckets (folders) with their media counts.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getImageBuckets(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.util.ImageBucket>> $completion) {
        return null;
    }
}