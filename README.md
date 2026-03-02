# Memory Album

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

### 🏷️ Image Tagging
- Tag images with custom labels (e.g. #picnic #familytime)
- Add tags to individual images from the viewer
- Remove tags via the close icon on tag chips
- Tags are stored as lowercase, deduplicated entries

### ✅ Multi-Select & Batch Tagging
- Long-press an image in the gallery to enter selection mode
- Select multiple images and apply tags to all at once
- Selection bar shows count and provides "Add Tags" and "Cancel" actions

### 🔍 Search
- Search remarks and tags from the toolbar
- Tag-matched and remark-matched results are merged and deduplicated
- Search results display tag chips alongside remark text

### 📁 Folder Organization
- Browse images organized by folders/albums
- Quick folder navigation with thumbnail previews
- Image count displayed for each folder

### 📷 Image Metadata Display
- View EXIF metadata including date taken, camera info, and resolution
- GPS location coordinates when available
- File size and filename information

### 👆 Intuitive Swipe Navigation
- **Swipe left/right**: Navigate between images
- **Swipe up**: Open the remark panel for the current image
- **Swipe down**: Close the remark panel
- **Tap**: Toggle UI visibility

### 💾 Backup & Restore
- Export all tags, remarks, and contacts to a portable JSON file
- Import a backup on a new device after transferring your photos
- Uses Android Storage Access Framework — save to Google Drive, Dropbox, or local storage
- Merge strategy: existing data is preserved; backup fills in gaps (empty remarks, missing tags/contacts)
- Images are matched by content hash, so metadata attaches automatically once photos are on the new device

## How It Works

1. **Image Hashing**: When you view an image, the app calculates a SHA-256 hash of the image's actual content (bytes). This hash is unique to the image content.

2. **Remark Storage**: When you save a remark, it's stored in a local Room database with the hash as the primary key.

3. **Recognition**: When you open any image (even if moved), the app calculates its hash and looks up any existing remarks in the database.

## Project Structure

```
app/src/main/java/com/hashalbum/app/
├── HashAlbumApp.kt          # Application class
├── data/
│   ├── AppDatabase.kt       # Room database (v5)
│   ├── BackupData.kt        # Backup/import data classes
│   ├── Contact.kt           # Contact + ImageContact entities
│   ├── ContactDao.kt        # Contact data access object
│   ├── GalleryImage.kt      # Image data class
│   ├── ImageData.kt         # Database entity
│   ├── ImageDataDao.kt      # Data access object
│   ├── ImagePath.kt         # Path tracking entity
│   ├── ImagePathDao.kt      # Path data access object
│   ├── ImageTag.kt          # Tag entity
│   ├── ImageTagDao.kt       # Tag data access object
│   ├── ImageRepository.kt   # Repository pattern
│   └── SearchResultItem.kt  # Search result model
├── ui/
│   ├── BackupActivity.kt    # Backup & restore screen
│   ├── BucketAdapter.kt     # Folder/album list adapter
│   ├── ContactsActivity.kt  # Contacts list screen
│   ├── ContactsAdapter.kt   # Contacts list adapter
│   ├── GalleryAdapter.kt    # RecyclerView adapter (with multi-select)
│   ├── GalleryViewModel.kt  # ViewModel for gallery
│   ├── ImagePagerAdapter.kt # ViewPager adapter
│   ├── ImageViewerActivity.kt # Full-screen image viewer
│   ├── SearchResultAdapter.kt # Search results adapter
│   └── MainActivity.kt      # Main gallery activity
└── util/
    ├── BackupManager.kt     # JSON serialization for backup/restore
    ├── ImageHasher.kt       # SHA-256 hash generation
    ├── ImageMetadataHelper.kt # EXIF metadata extraction
    ├── MediaStoreHelper.kt  # MediaStore queries
    ├── PathValidator.kt     # Stale path cleanup
    └── TagParser.kt         # Tag input parsing utility
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

- `READ_MEDIA_IMAGES` + `READ_MEDIA_VIDEO` (Android 13+)
- `READ_EXTERNAL_STORAGE` (Android 12 and below)
- `READ_CONTACTS` (on-demand, for importing contacts from the phone's address book)

## Building the Project

Requires **JDK 17** (AGP 8.4.0 needs 17+):

```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk ./gradlew assembleDebug
```

Install via ADB:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Usage

1. **Grant permission** to access photos when prompted
2. **Browse** your photo gallery in the grid view
3. **Tap** an image to view it full-screen
4. **Swipe left/right** to navigate between images
5. **Swipe up** to open the remark panel
6. **Enter your remark** and tap "Save Remark"
7. Images with remarks show an orange indicator dot
8. **Long-press** to enter multi-select mode for batch tagging/remarks/contacts
9. **Overflow menu → Backup & Restore** to export or import metadata

## Technical Notes

### Hash Generation
The app uses SHA-256 to hash the entire image content. This ensures:
- Unique identification even for visually similar images
- Recognition survives file moves/renames
- Detection of modified images (different hash)

### Database Schema (v5)
```sql
CREATE TABLE image_data (
    hash TEXT PRIMARY KEY,
    remark TEXT,
    lastKnownPath TEXT,
    createdAt INTEGER,
    updatedAt INTEGER,
    mediaType TEXT DEFAULT 'image'
);

CREATE TABLE image_paths (
    hash TEXT NOT NULL,
    path TEXT NOT NULL,
    lastSeen INTEGER,
    isValid INTEGER,
    PRIMARY KEY(hash, path),
    FOREIGN KEY(hash) REFERENCES image_data(hash) ON DELETE CASCADE
);

CREATE TABLE image_tags (
    hash TEXT NOT NULL,
    tag TEXT NOT NULL,
    createdAt INTEGER,
    PRIMARY KEY(hash, tag),
    FOREIGN KEY(hash) REFERENCES image_data(hash) ON DELETE CASCADE
);

CREATE TABLE contacts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    createdAt INTEGER
);

CREATE TABLE image_contacts (
    hash TEXT NOT NULL,
    contactId INTEGER NOT NULL,
    createdAt INTEGER,
    PRIMARY KEY(hash, contactId),
    FOREIGN KEY(hash) REFERENCES image_data(hash) ON DELETE CASCADE,
    FOREIGN KEY(contactId) REFERENCES contacts(id) ON DELETE CASCADE
);
```

### Backup Format
Backups are versioned JSON files containing all image metadata (remarks, tags, contact associations) keyed by content hash. File paths are intentionally excluded — they are device-specific and re-discovered automatically by the app.

```json
{
  "version": 1,
  "exportDate": "2026-03-02",
  "exportDateMs": 1740873600000,
  "images": [
    {
      "hash": "abc123...",
      "remark": "Beach trip",
      "mediaType": "image",
      "createdAt": 1234567890,
      "updatedAt": 1234567890,
      "tags": ["beach", "family"],
      "contactIds": [1]
    }
  ],
  "contacts": [
    { "id": 1, "name": "John Doe", "createdAt": 1234567890 }
  ]
}
```

## Feature Status

- [x] Folder/album organization
- [x] Search remarks and tags
- [x] Image tags support
- [x] Batch tagging / remarks / contacts (multi-select)
- [x] Contact tagging (link people to images)
- [x] Video support
- [x] Share images/videos
- [x] Delete images
- [x] Pinch-to-zoom in viewer
- [x] Export/import metadata backup

## License

MIT License
