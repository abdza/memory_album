package com.hashalbum.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0002J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u00a8\u0006\u0019"}, d2 = {"Lcom/hashalbum/app/util/ImageMetadataHelper;", "", "()V", "formatCoordinates", "", "lat", "", "lon", "formatDate", "millis", "", "formatDuration", "formatExifDate", "exifDate", "formatFileSize", "bytes", "getImageMetadata", "Lcom/hashalbum/app/util/ImageMetadata;", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "getMetadata", "(Landroid/content/Context;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVideoMetadata", "app_debug"})
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
    
    private final com.hashalbum.app.util.ImageMetadata getVideoMetadata(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    private final com.hashalbum.app.util.ImageMetadata getImageMetadata(android.content.Context context, android.net.Uri uri) {
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
    
    private final java.lang.String formatDuration(long millis) {
        return null;
    }
}