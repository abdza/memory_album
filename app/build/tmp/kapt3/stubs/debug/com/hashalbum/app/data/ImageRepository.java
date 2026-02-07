package com.hashalbum.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\tH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0018\u0010\u001e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\b2\u0006\u0010\u0011\u001a\u00020\u0012J\u001c\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010#J&\u0010$\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010&J\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010(\u001a\u00020\u0012J\u001e\u0010)\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0014J&\u0010*\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010+\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010-J\u001e\u0010.\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006/"}, d2 = {"Lcom/hashalbum/app/data/ImageRepository;", "", "imageDataDao", "Lcom/hashalbum/app/data/ImageDataDao;", "imagePathDao", "Lcom/hashalbum/app/data/ImagePathDao;", "(Lcom/hashalbum/app/data/ImageDataDao;Lcom/hashalbum/app/data/ImagePathDao;)V", "allImages", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hashalbum/app/data/ImageData;", "getAllImages", "()Lkotlinx/coroutines/flow/Flow;", "imagesWithRemarks", "getImagesWithRemarks", "addOrUpdatePath", "", "hash", "", "path", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStaleInvalidPaths", "days", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPathsSync", "Lcom/hashalbum/app/data/ImagePath;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByHash", "getByHashFlow", "getPathsForHashSync", "insert", "imageData", "(Lcom/hashalbum/app/data/ImageData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveOrUpdateRemark", "remark", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByRemark", "query", "updatePath", "updatePathValidity", "isValid", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRemark", "app_debug"})
public final class ImageRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.hashalbum.app.data.ImageDataDao imageDataDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hashalbum.app.data.ImagePathDao imagePathDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> allImages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> imagesWithRemarks = null;
    
    public ImageRepository(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageDataDao imageDataDao, @org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImagePathDao imagePathDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> getAllImages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> getImagesWithRemarks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> searchByRemark(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getByHash(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hashalbum.app.data.ImageData> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.hashalbum.app.data.ImageData> getByHashFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String hash) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageData imageData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateRemark(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String remark, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePath(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveOrUpdateRemark(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String remark, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdatePath(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPathsForHashSync(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.ImagePath>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePathValidity(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, boolean isValid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteStaleInvalidPaths(int days, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllPathsSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.ImagePath>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}