package com.hashalbum.app.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ImageDataDao _imageDataDao;

  private volatile ImagePathDao _imagePathDao;

  private volatile ImageTagDao _imageTagDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `image_data` (`hash` TEXT NOT NULL, `remark` TEXT NOT NULL, `lastKnownPath` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`hash`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `image_paths` (`hash` TEXT NOT NULL, `path` TEXT NOT NULL, `lastSeen` INTEGER NOT NULL, `isValid` INTEGER NOT NULL, PRIMARY KEY(`hash`, `path`), FOREIGN KEY(`hash`) REFERENCES `image_data`(`hash`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `image_tags` (`hash` TEXT NOT NULL, `tag` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`hash`, `tag`), FOREIGN KEY(`hash`) REFERENCES `image_data`(`hash`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'be73ae9a6c007e187d30725f99e1eda1')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `image_data`");
        db.execSQL("DROP TABLE IF EXISTS `image_paths`");
        db.execSQL("DROP TABLE IF EXISTS `image_tags`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsImageData = new HashMap<String, TableInfo.Column>(5);
        _columnsImageData.put("hash", new TableInfo.Column("hash", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImageData.put("remark", new TableInfo.Column("remark", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImageData.put("lastKnownPath", new TableInfo.Column("lastKnownPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImageData.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImageData.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysImageData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesImageData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoImageData = new TableInfo("image_data", _columnsImageData, _foreignKeysImageData, _indicesImageData);
        final TableInfo _existingImageData = TableInfo.read(db, "image_data");
        if (!_infoImageData.equals(_existingImageData)) {
          return new RoomOpenHelper.ValidationResult(false, "image_data(com.hashalbum.app.data.ImageData).\n"
                  + " Expected:\n" + _infoImageData + "\n"
                  + " Found:\n" + _existingImageData);
        }
        final HashMap<String, TableInfo.Column> _columnsImagePaths = new HashMap<String, TableInfo.Column>(4);
        _columnsImagePaths.put("hash", new TableInfo.Column("hash", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImagePaths.put("path", new TableInfo.Column("path", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImagePaths.put("lastSeen", new TableInfo.Column("lastSeen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImagePaths.put("isValid", new TableInfo.Column("isValid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysImagePaths = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysImagePaths.add(new TableInfo.ForeignKey("image_data", "CASCADE", "NO ACTION", Arrays.asList("hash"), Arrays.asList("hash")));
        final HashSet<TableInfo.Index> _indicesImagePaths = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoImagePaths = new TableInfo("image_paths", _columnsImagePaths, _foreignKeysImagePaths, _indicesImagePaths);
        final TableInfo _existingImagePaths = TableInfo.read(db, "image_paths");
        if (!_infoImagePaths.equals(_existingImagePaths)) {
          return new RoomOpenHelper.ValidationResult(false, "image_paths(com.hashalbum.app.data.ImagePath).\n"
                  + " Expected:\n" + _infoImagePaths + "\n"
                  + " Found:\n" + _existingImagePaths);
        }
        final HashMap<String, TableInfo.Column> _columnsImageTags = new HashMap<String, TableInfo.Column>(3);
        _columnsImageTags.put("hash", new TableInfo.Column("hash", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImageTags.put("tag", new TableInfo.Column("tag", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImageTags.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysImageTags = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysImageTags.add(new TableInfo.ForeignKey("image_data", "CASCADE", "NO ACTION", Arrays.asList("hash"), Arrays.asList("hash")));
        final HashSet<TableInfo.Index> _indicesImageTags = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoImageTags = new TableInfo("image_tags", _columnsImageTags, _foreignKeysImageTags, _indicesImageTags);
        final TableInfo _existingImageTags = TableInfo.read(db, "image_tags");
        if (!_infoImageTags.equals(_existingImageTags)) {
          return new RoomOpenHelper.ValidationResult(false, "image_tags(com.hashalbum.app.data.ImageTag).\n"
                  + " Expected:\n" + _infoImageTags + "\n"
                  + " Found:\n" + _existingImageTags);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "be73ae9a6c007e187d30725f99e1eda1", "e7582a7a58ed17cf3b6a25a5d9dd2226");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "image_data","image_paths","image_tags");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `image_data`");
      _db.execSQL("DELETE FROM `image_paths`");
      _db.execSQL("DELETE FROM `image_tags`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ImageDataDao.class, ImageDataDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ImagePathDao.class, ImagePathDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ImageTagDao.class, ImageTagDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ImageDataDao imageDataDao() {
    if (_imageDataDao != null) {
      return _imageDataDao;
    } else {
      synchronized(this) {
        if(_imageDataDao == null) {
          _imageDataDao = new ImageDataDao_Impl(this);
        }
        return _imageDataDao;
      }
    }
  }

  @Override
  public ImagePathDao imagePathDao() {
    if (_imagePathDao != null) {
      return _imagePathDao;
    } else {
      synchronized(this) {
        if(_imagePathDao == null) {
          _imagePathDao = new ImagePathDao_Impl(this);
        }
        return _imagePathDao;
      }
    }
  }

  @Override
  public ImageTagDao imageTagDao() {
    if (_imageTagDao != null) {
      return _imageTagDao;
    } else {
      synchronized(this) {
        if(_imageTagDao == null) {
          _imageTagDao = new ImageTagDao_Impl(this);
        }
        return _imageTagDao;
      }
    }
  }
}
