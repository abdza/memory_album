package com.hashalbum.app.util;

/**
 * Utility class for generating SHA-256 hashes of images.
 * The hash is based on the actual image content, so even if the image
 * is moved to a different location, the same hash will be generated.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J*\u0010\u0012\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0013\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/hashalbum/app/util/ImageHasher;", "", "()V", "ALGORITHM", "", "BUFFER_SIZE", "", "HEX_CHARS", "", "bytesToHex", "bytes", "", "generateHash", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "(Landroid/content/Context;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateQuickHash", "maxBytes", "(Landroid/content/Context;Landroid/net/Uri;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ImageHasher {
    private static final int BUFFER_SIZE = 8192;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ALGORITHM = "SHA-256";
    @org.jetbrains.annotations.NotNull()
    private static final char[] HEX_CHARS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.hashalbum.app.util.ImageHasher INSTANCE = null;
    
    private ImageHasher() {
        super();
    }
    
    /**
     * Generate a SHA-256 hash of the image content from a Uri.
     * This hash is based on the actual bytes of the image, not its location.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateHash(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Generate a partial hash using only the first portion of the image.
     * Useful for quick comparison but less accurate for modified images.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateQuickHash(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, int maxBytes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Convert byte array to hexadecimal string.
     */
    private final java.lang.String bytesToHex(byte[] bytes) {
        return null;
    }
}