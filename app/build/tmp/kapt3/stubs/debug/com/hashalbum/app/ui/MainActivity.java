package com.hashalbum.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\b\u0010\u001b\u001a\u00020\u0016H\u0002J\b\u0010\u001c\u001a\u00020\u0016H\u0017J\u0012\u0010\u001d\u001a\u00020\u00162\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u0016H\u0002J\b\u0010%\u001a\u00020\u0016H\u0002J\b\u0010&\u001a\u00020\u0016H\u0002J\b\u0010\'\u001a\u00020\u0016H\u0002J\b\u0010(\u001a\u00020\u0016H\u0002J\b\u0010)\u001a\u00020\u0016H\u0002J\b\u0010*\u001a\u00020\u0016H\u0002J\b\u0010+\u001a\u00020\u0016H\u0002J\b\u0010,\u001a\u00020\u0016H\u0002J\u0010\u0010-\u001a\u00020\u00162\u0006\u0010.\u001a\u00020/H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012\u00a8\u00060"}, d2 = {"Lcom/hashalbum/app/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/hashalbum/app/databinding/ActivityMainBinding;", "bucketAdapter", "Lcom/hashalbum/app/ui/BucketAdapter;", "galleryAdapter", "Lcom/hashalbum/app/ui/GalleryAdapter;", "permissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "searchResultAdapter", "Lcom/hashalbum/app/ui/SearchResultAdapter;", "viewModel", "Lcom/hashalbum/app/ui/GalleryViewModel;", "getViewModel", "()Lcom/hashalbum/app/ui/GalleryViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "checkPermissionAndLoad", "", "enterSelectionMode", "exitSelectionMode", "loadBuckets", "loadImages", "observeViewModel", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "setupBucketDrawer", "setupRecyclerView", "setupSelectionBar", "setupSwipeRefresh", "setupToolbar", "showBatchRemarkDialog", "showBatchTagDialog", "switchToGalleryMode", "switchToSearchMode", "updateSelectionCount", "count", "", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.hashalbum.app.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.hashalbum.app.ui.GalleryAdapter galleryAdapter;
    private com.hashalbum.app.ui.BucketAdapter bucketAdapter;
    private com.hashalbum.app.ui.SearchResultAdapter searchResultAdapter;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> permissionLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    private final com.hashalbum.app.ui.GalleryViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupBucketDrawer() {
    }
    
    private final void setupSwipeRefresh() {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    private final void loadBuckets() {
    }
    
    private final void switchToSearchMode() {
    }
    
    private final void switchToGalleryMode() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void checkPermissionAndLoad() {
    }
    
    private final void setupSelectionBar() {
    }
    
    private final void enterSelectionMode() {
    }
    
    private final void exitSelectionMode() {
    }
    
    private final void updateSelectionCount(int count) {
    }
    
    private final void showBatchTagDialog() {
    }
    
    private final void showBatchRemarkDialog() {
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    public void onBackPressed() {
    }
    
    private final void loadImages() {
    }
}