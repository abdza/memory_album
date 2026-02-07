package com.hashalbum.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00062\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0016J&\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u001c\u001a\u00020\u0010J\u001e\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001eR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n\u00a8\u0006 "}, d2 = {"Lcom/hashalbum/app/data/ImageRepository;", "", "imageDataDao", "Lcom/hashalbum/app/data/ImageDataDao;", "(Lcom/hashalbum/app/data/ImageDataDao;)V", "allImages", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hashalbum/app/data/ImageData;", "getAllImages", "()Lkotlinx/coroutines/flow/Flow;", "imagesWithRemarks", "getImagesWithRemarks", "delete", "", "hash", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByHash", "getByHashFlow", "insert", "imageData", "(Lcom/hashalbum/app/data/ImageData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveOrUpdateRemark", "remark", "path", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByRemark", "query", "updatePath", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRemark", "app_debug"})
public final class ImageRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.hashalbum.app.data.ImageDataDao imageDataDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> allImages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> imagesWithRemarks = null;
    
    public ImageRepository(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageDataDao imageDataDao) {
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
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}