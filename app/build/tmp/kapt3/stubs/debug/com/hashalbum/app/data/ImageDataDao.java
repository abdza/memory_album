package com.hashalbum.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\b\u001a\u00020\tH\'J\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0013\u001a\u00020\tH\'J\u0016\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J(\u0010\u0015\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0019J(\u0010\u001a\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/hashalbum/app/data/ImageDataDao;", "", "delete", "", "imageData", "Lcom/hashalbum/app/data/ImageData;", "(Lcom/hashalbum/app/data/ImageData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByHash", "hash", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllImages", "Lkotlinx/coroutines/flow/Flow;", "", "getByHash", "getByHashFlow", "getImagesWithRemarks", "insert", "searchByRemark", "query", "update", "updatePath", "path", "updatedAt", "", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRemark", "remark", "app_debug"})
@androidx.room.Dao()
public abstract interface ImageDataDao {
    
    @androidx.room.Query(value = "SELECT * FROM image_data WHERE hash = :hash")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByHash(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hashalbum.app.data.ImageData> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM image_data WHERE hash = :hash")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.hashalbum.app.data.ImageData> getByHashFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String hash);
    
    @androidx.room.Query(value = "SELECT * FROM image_data ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> getAllImages();
    
    @androidx.room.Query(value = "SELECT * FROM image_data WHERE remark != \'\' ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> getImagesWithRemarks();
    
    @androidx.room.Query(value = "SELECT * FROM image_data WHERE remark LIKE \'%\' || :query || \'%\' ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImageData>> searchByRemark(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageData imageData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageData imageData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE image_data SET remark = :remark, updatedAt = :updatedAt WHERE hash = :hash")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateRemark(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String remark, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE image_data SET lastKnownPath = :path, updatedAt = :updatedAt WHERE hash = :hash")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePath(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImageData imageData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM image_data WHERE hash = :hash")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteByHash(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}