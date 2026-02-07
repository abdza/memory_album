package com.hashalbum.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rH\'J\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0017J(\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u001aJ&\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006\u001f"}, d2 = {"Lcom/hashalbum/app/data/ImagePathDao;", "", "deletePath", "", "hash", "", "path", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStaleInvalidPaths", "cutoffTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPaths", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hashalbum/app/data/ImagePath;", "getAllPathsSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPathsForHash", "getPathsForHashSync", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPath", "imagePath", "(Lcom/hashalbum/app/data/ImagePath;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLastSeen", "lastSeen", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateValidity", "isValid", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface ImagePathDao {
    
    @androidx.room.Query(value = "SELECT * FROM image_paths WHERE hash = :hash")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImagePath>> getPathsForHash(@org.jetbrains.annotations.NotNull()
    java.lang.String hash);
    
    @androidx.room.Query(value = "SELECT * FROM image_paths WHERE hash = :hash")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPathsForHashSync(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.ImagePath>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPath(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.data.ImagePath imagePath, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE image_paths SET lastSeen = :lastSeen WHERE hash = :hash AND path = :path")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLastSeen(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, long lastSeen, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE image_paths SET isValid = :isValid WHERE hash = :hash AND path = :path")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateValidity(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, boolean isValid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM image_paths WHERE hash = :hash AND path = :path")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePath(@org.jetbrains.annotations.NotNull()
    java.lang.String hash, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM image_paths WHERE isValid = 0 AND lastSeen < :cutoffTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteStaleInvalidPaths(long cutoffTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM image_paths")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hashalbum.app.data.ImagePath>> getAllPaths();
    
    @androidx.room.Query(value = "SELECT * FROM image_paths")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllPathsSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hashalbum.app.data.ImagePath>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}