# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build

Requires JDK 17 (AGP 8.4.0 needs 17+, Kotlin 1.9.22 kapt breaks on JDK 25):

```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk ./gradlew assembleDebug
```

Install to device via ADB over network. Last known address: `192.168.1.5:40001`. Reconfirm with user or ask for new IP:port if needed (same session typically reuses the same address):
```bash
~/Android/Sdk/platform-tools/adb connect <ip>:<port>
~/Android/Sdk/platform-tools/adb -s <ip>:<port> install -r app/build/outputs/apk/debug/app-debug.apk
```

No tests exist yet. Test dependencies (JUnit 4, Espresso) are configured but unused.

## Architecture

MVVM Android app (single module, Kotlin, View Binding) for managing photo metadata by content hash.

**Data flow:** Activities/Adapters → GalleryViewModel → ImageRepository → Room DAOs → AppDatabase (v5)

**Key design decision:** Images are identified by SHA-256 content hash (not path), so metadata survives moves/renames. Multiple file paths can point to the same image.

### Data Layer (`data/`)

- **Room entities:** `ImageData` (hash PK, remark), `ImagePath` (hash+path composite PK), `ImageTag`, `Contact`, image_contacts junction table
- **AppDatabase** — version 5 with migration chain (1→2→3→4→5). New schema changes need a new migration in the companion object.
- **ImageRepository** — single repository orchestrating all four DAOs (~35 methods)

### UI Layer (`ui/`)

- **MainActivity** — Gallery grid (3-column GridLayoutManager) with date headers spanning full width. Includes folder drawer, search, multi-select batch operations (tags/remarks/contacts), and FAB date picker.
- **ImageViewerActivity** — Full-screen ViewPager2 with gesture-driven remark panel (swipe up/down), EXIF display, tag/contact editing.
- **ContactsActivity** — Contact list with image counts, click to view contact's images.
- **GalleryAdapter** — Handles two view types: DateHeader (span 3) and ImageItem (span 1). Selection mode with long-press entry.
- **GalleryViewModel** — Central state via StateFlow. Manages hash cache, image grouping by date, search (remark + tag merged/deduped), and background path validation.

### Util Layer (`util/`)

- **ImageHasher** — SHA-256 hashing (full and quick variants)
- **MediaStoreHelper** — MediaStore queries, bucket/folder aggregation, sorted by DATE_MODIFIED DESC
- **TagParser** — Parses whitespace/comma-separated tags, lowercased, deduped
- **PathValidator** — Checks Uri validity; stale paths (30+ days) auto-cleaned on startup

## Resource Conventions

- Color accent is `@color/accent` (not `colorAccent`)
- Date headers use "Today", "Yesterday", or `SimpleDateFormat("EEE, MMM d, yyyy")` — see `GalleryViewModel.groupImagesByDate()`
- String resources in `values/strings.xml` (53 entries); avoid hardcoded UI text

## Permissions

Runtime-requested: `READ_MEDIA_IMAGES` + `READ_MEDIA_VIDEO` (API 33+) / `READ_EXTERNAL_STORAGE` (older), `READ_CONTACTS` (on-demand for contact tagging).
