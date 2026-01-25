package com.hashalbum.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0010\u0011B\u001f\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\u00020\u00072\n\u0010\n\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u001c\u0010\f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0016R \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/hashalbum/app/ui/GalleryAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/hashalbum/app/data/GalleryImage;", "Lcom/hashalbum/app/ui/GalleryAdapter$ImageViewHolder;", "onImageClick", "Lkotlin/Function2;", "", "", "(Lkotlin/jvm/functions/Function2;)V", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ImageDiffCallback", "ImageViewHolder", "app_debug"})
public final class GalleryAdapter extends androidx.recyclerview.widget.ListAdapter<com.hashalbum.app.data.GalleryImage, com.hashalbum.app.ui.GalleryAdapter.ImageViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<com.hashalbum.app.data.GalleryImage, java.lang.Integer, kotlin.Unit> onImageClick = null;
    
    public GalleryAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.hashalbum.app.data.GalleryImage, ? super java.lang.Integer, kotlin.Unit> onImageClick) {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.hashalbum.app.ui.GalleryAdapter.ImageViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.hashalbum.app.ui.GalleryAdapter.ImageViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/hashalbum/app/ui/GalleryAdapter$ImageDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/hashalbum/app/data/GalleryImage;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    public static final class ImageDiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.hashalbum.app.data.GalleryImage> {
        
        public ImageDiffCallback() {
            super();
        }
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.hashalbum.app.data.GalleryImage oldItem, @org.jetbrains.annotations.NotNull()
        com.hashalbum.app.data.GalleryImage newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.hashalbum.app.data.GalleryImage oldItem, @org.jetbrains.annotations.NotNull()
        com.hashalbum.app.data.GalleryImage newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/hashalbum/app/ui/GalleryAdapter$ImageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/hashalbum/app/ui/GalleryAdapter;Landroid/view/View;)V", "imageView", "Landroid/widget/ImageView;", "remarkIndicator", "bind", "", "image", "Lcom/hashalbum/app/data/GalleryImage;", "position", "", "showRemarkIndicator", "show", "", "app_debug"})
    public final class ImageViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView imageView = null;
        @org.jetbrains.annotations.NotNull()
        private final android.view.View remarkIndicator = null;
        
        public ImageViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.hashalbum.app.data.GalleryImage image, int position) {
        }
        
        public final void showRemarkIndicator(boolean show) {
        }
    }
}