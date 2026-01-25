# Hash Album

An Android picture album app with a unique hash-based image identification feature. Even if you move a picture to a different folder, the app can still recognize it and load its associated remarks.

## Key Features

### 🔐 Hash-Based Image Identification
- Each image is identified by its SHA-256 content hash, not its file path
- Move images anywhere on your device, and the app will still recognize them
- Remarks are permanently linked to the image content, not location

### 📝 Image Remarks
- Add personal notes/remarks to any image
- Swipe up on an image to reveal the remark panel
- Remarks are saved locally and persist across app sessions

### 👆 Intuitive Swipe Navigation
- **Swipe left/right**: Navigate between images
- **Swipe up**: Open the remark panel for the current image
- **Swipe down**: Close the remark panel
- **Tap**: Toggle UI visibility

## How It Works

1. **Image Hashing**: When you view an image, the app calculates a SHA-256 hash of the image's actual content (bytes). This hash is unique to the image content.

2. **Remark Storage**: When you save a remark, it's stored in a local Room database with the hash as the primary key.

3. **Recognition**: When you open any image (even if moved), the app calculates its hash and looks up any existing remarks in the database.

## Project Structure

```
app/src/main/java/com/hashalbum/app/
├── HashAlbumApp.kt          # Application class
├── data/
│   ├── AppDatabase.kt       # Room database
│   ├── GalleryImage.kt      # Image data class
│   ├── ImageData.kt         # Database entity
│   ├── ImageDataDao.kt      # Data access object
│   └── ImageRepository.kt   # Repository pattern
├── ui/
│   ├── GalleryAdapter.kt    # RecyclerView adapter
│   ├── GalleryViewModel.kt  # ViewModel for gallery
│   ├── ImagePagerAdapter.kt # ViewPager adapter
│   ├── ImageViewerActivity.kt # Full-screen image viewer
│   └── MainActivity.kt      # Main gallery activity
└── util/
    ├── ImageHasher.kt       # SHA-256 hash generation
    └── MediaStoreHelper.kt  # MediaStore queries
```

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM
- **Database**: Room
- **Image Loading**: Glide
- **UI Components**: 
  - RecyclerView (grid gallery)
  - ViewPager2 (image swipe navigation)
  - Material Design components
- **Async**: Kotlin Coroutines + Flow

## Permissions Required

- `READ_MEDIA_IMAGES` (Android 13+)
- `READ_EXTERNAL_STORAGE` (Android 12 and below)

## Building the Project

1. Open the project in Android Studio
2. Sync Gradle files
3. Build and run on a device or emulator

## Usage

1. **Grant permission** to access photos when prompted
2. **Browse** your photo gallery in the grid view
3. **Tap** an image to view it full-screen
4. **Swipe left/right** to navigate between images
5. **Swipe up** to open the remark panel
6. **Enter your remark** and tap "Save Remark"
7. Images with remarks show an orange indicator dot

## Technical Notes

### Hash Generation
The app uses SHA-256 to hash the entire image content. This ensures:
- Unique identification even for visually similar images
- Recognition survives file moves/renames
- Detection of modified images (different hash)

### Database Schema
```sql
CREATE TABLE image_data (
    hash TEXT PRIMARY KEY,
    remark TEXT,
    lastKnownPath TEXT,
    createdAt INTEGER,
    updatedAt INTEGER
);
```

## Future Enhancements

- [ ] Folder/album organization
- [ ] Search remarks
- [ ] Export/import remarks
- [ ] Cloud sync
- [ ] Image tags support
- [ ] Batch remark editing

## License

MIT License
