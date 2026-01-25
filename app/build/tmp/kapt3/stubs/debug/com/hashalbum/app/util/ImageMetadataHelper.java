package com.hashalbum.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/hashalbum/app/util/ImageMetadataHelper;", "", "()V", "formatCoordinates", "", "lat", "", "lon", "formatDate", "millis", "", "formatExifDate", "exifDate", "formatFileSize", "bytes", "getMetadata", "Lcom/hashalbum/app/util/ImageMetadata;", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "(Landroid/content/Context;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ImageMetadataHelper {
    @org.jetbrains.annotations.NotNull()
    public static final com.hashalbum.app.util.ImageMetadataHelper INSTANCE = null;
    
    private ImageMetadataHelper() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMetadata(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hashalbum.app.util.ImageMetadata> $completion) {
        return null;
    }
    
    private final java.lang.String formatDate(long millis) {
        return null;
    }
    
    private final java.lang.String formatExifDate(java.lang.String exifDate) {
        return null;
    }
    
    private final java.lang.String formatCoordinates(double lat, double lon) {
        return null;
    }
    
    private final java.lang.String formatFileSize(long bytes) {
        return null;
    }
}