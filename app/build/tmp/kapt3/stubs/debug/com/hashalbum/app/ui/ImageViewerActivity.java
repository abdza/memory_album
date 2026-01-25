package com.hashalbum.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 62\u00020\u0001:\u00016B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\b\u0010#\u001a\u00020!H\u0002J\b\u0010$\u001a\u00020!H\u0016J\u0012\u0010%\u001a\u00020!2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0014J\b\u0010(\u001a\u00020!H\u0002J\b\u0010)\u001a\u00020!H\u0002J\b\u0010*\u001a\u00020!H\u0002J\b\u0010+\u001a\u00020!H\u0002J\b\u0010,\u001a\u00020!H\u0002J\b\u0010-\u001a\u00020!H\u0002J\b\u0010.\u001a\u00020!H\u0002J\b\u0010/\u001a\u00020!H\u0002J\b\u00100\u001a\u00020!H\u0002J\b\u00101\u001a\u00020!H\u0002J\b\u00102\u001a\u00020!H\u0002J\b\u00103\u001a\u00020!H\u0002J\b\u00104\u001a\u00020!H\u0002J\b\u00105\u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001a\u00a8\u00067"}, d2 = {"Lcom/hashalbum/app/ui/ImageViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/hashalbum/app/databinding/ActivityImageViewerBinding;", "currentMetadata", "Lcom/hashalbum/app/util/ImageMetadata;", "currentPosition", "", "currentRemark", "", "gestureDetector", "Landroidx/core/view/GestureDetectorCompat;", "imageAdapter", "Lcom/hashalbum/app/ui/ImagePagerAdapter;", "imageUris", "", "Landroid/net/Uri;", "isEditMode", "", "isRemarkPanelShown", "remarkPanelAnimator", "Landroid/animation/ObjectAnimator;", "viewModel", "Lcom/hashalbum/app/ui/GalleryViewModel;", "getViewModel", "()Lcom/hashalbum/app/ui/GalleryViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "dispatchTouchEvent", "ev", "Landroid/view/MotionEvent;", "hideKeyboard", "", "hideRemarkPanel", "loadCurrentImageRemark", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "parseIntent", "prepareDisplayMode", "prepareEditMode", "saveCurrentRemark", "setRemarkDisplayMode", "setRemarkEditMode", "setupCloseButton", "setupGestureDetection", "setupRemarkPanel", "setupViewPager", "showRemarkPanel", "toggleUiVisibility", "updateCounter", "updateMetadataDisplay", "Companion", "app_debug"})
public final class ImageViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_IMAGE_POSITION = "extra_image_position";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_IMAGE_URIS = "extra_image_uris";
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private com.hashalbum.app.databinding.ActivityImageViewerBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.hashalbum.app.ui.ImagePagerAdapter imageAdapter;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<? extends android.net.Uri> imageUris;
    private int currentPosition = 0;
    private boolean isRemarkPanelShown = false;
    private boolean isEditMode = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentRemark = "";
    @org.jetbrains.annotations.Nullable()
    private com.hashalbum.app.util.ImageMetadata currentMetadata;
    @org.jetbrains.annotations.Nullable()
    private android.animation.ObjectAnimator remarkPanelAnimator;
    private androidx.core.view.GestureDetectorCompat gestureDetector;
    @org.jetbrains.annotations.NotNull()
    public static final com.hashalbum.app.ui.ImageViewerActivity.Companion Companion = null;
    
    public ImageViewerActivity() {
        super();
    }
    
    private final com.hashalbum.app.ui.GalleryViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void parseIntent() {
    }
    
    private final void setupViewPager() {
    }
    
    private final void setupGestureDetection() {
    }
    
    private final void setupRemarkPanel() {
    }
    
    private final void setRemarkDisplayMode() {
    }
    
    private final void setRemarkEditMode() {
    }
    
    private final void setupCloseButton() {
    }
    
    private final void showRemarkPanel() {
    }
    
    private final void hideRemarkPanel() {
    }
    
    private final void loadCurrentImageRemark() {
    }
    
    private final void updateMetadataDisplay() {
    }
    
    private final void prepareDisplayMode() {
    }
    
    private final void prepareEditMode() {
    }
    
    private final void saveCurrentRemark() {
    }
    
    private final void updateCounter() {
    }
    
    private final void toggleUiVisibility() {
    }
    
    private final void hideKeyboard() {
    }
    
    @java.lang.Override()
    public boolean dispatchTouchEvent(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent ev) {
        return false;
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/hashalbum/app/ui/ImageViewerActivity$Companion;", "", "()V", "EXTRA_IMAGE_POSITION", "", "EXTRA_IMAGE_URIS", "SWIPE_THRESHOLD", "", "SWIPE_VELOCITY_THRESHOLD", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}