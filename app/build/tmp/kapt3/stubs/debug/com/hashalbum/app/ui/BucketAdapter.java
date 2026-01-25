package com.hashalbum.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0014\u0015B\u001b\u0012\u0014\u0010\u0004\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\n\u001a\u00020\u00062\n\u0010\u000b\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0016J\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\tR\u001c\u0010\u0004\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/hashalbum/app/ui/BucketAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/hashalbum/app/util/ImageBucket;", "Lcom/hashalbum/app/ui/BucketAdapter$BucketViewHolder;", "onBucketClick", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "showAllImages", "", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setShowAllImages", "show", "BucketDiffCallback", "BucketViewHolder", "app_debug"})
public final class BucketAdapter extends androidx.recyclerview.widget.ListAdapter<com.hashalbum.app.util.ImageBucket, com.hashalbum.app.ui.BucketAdapter.BucketViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.hashalbum.app.util.ImageBucket, kotlin.Unit> onBucketClick = null;
    private boolean showAllImages = true;
    
    public BucketAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.hashalbum.app.util.ImageBucket, kotlin.Unit> onBucketClick) {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.hashalbum.app.ui.BucketAdapter.BucketViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.ui.BucketAdapter.BucketViewHolder holder, int position) {
    }
    
    public final void setShowAllImages(boolean show) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/hashalbum/app/ui/BucketAdapter$BucketDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/hashalbum/app/util/ImageBucket;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    public static final class BucketDiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.hashalbum.app.util.ImageBucket> {
        
        public BucketDiffCallback() {
            super();
        }
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.hashalbum.app.util.ImageBucket oldItem, @org.jetbrains.annotations.NotNull()
        com.hashalbum.app.util.ImageBucket newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.hashalbum.app.util.ImageBucket oldItem, @org.jetbrains.annotations.NotNull()
        com.hashalbum.app.util.ImageBucket newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/hashalbum/app/ui/BucketAdapter$BucketViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/hashalbum/app/ui/BucketAdapter;Landroid/view/View;)V", "count", "Landroid/widget/TextView;", "name", "thumbnail", "Landroid/widget/ImageView;", "bind", "", "bucket", "Lcom/hashalbum/app/util/ImageBucket;", "app_debug"})
    public final class BucketViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView thumbnail = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView name = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView count = null;
        
        public BucketViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.hashalbum.app.util.ImageBucket bucket) {
        }
    }
}