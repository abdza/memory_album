package com.hashalbum.app.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ImageDataDao_Impl implements ImageDataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ImageData> __insertionAdapterOfImageData;

  private final EntityDeletionOrUpdateAdapter<ImageData> __deletionAdapterOfImageData;

  private final EntityDeletionOrUpdateAdapter<ImageData> __updateAdapterOfImageData;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRemark;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePath;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByHash;

  public ImageDataDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfImageData = new EntityInsertionAdapter<ImageData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `image_data` (`hash`,`remark`,`lastKnownPath`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ImageData entity) {
        if (entity.getHash() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getHash());
        }
        if (entity.getRemark() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRemark());
        }
        if (entity.getLastKnownPath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastKnownPath());
        }
        statement.bindLong(4, entity.getCreatedAt());
        statement.bindLong(5, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfImageData = new EntityDeletionOrUpdateAdapter<ImageData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `image_data` WHERE `hash` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ImageData entity) {
        if (entity.getHash() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getHash());
        }
      }
    };
    this.__updateAdapterOfImageData = new EntityDeletionOrUpdateAdapter<ImageData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `image_data` SET `hash` = ?,`remark` = ?,`lastKnownPath` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `hash` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ImageData entity) {
        if (entity.getHash() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getHash());
        }
        if (entity.getRemark() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRemark());
        }
        if (entity.getLastKnownPath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastKnownPath());
        }
        statement.bindLong(4, entity.getCreatedAt());
        statement.bindLong(5, entity.getUpdatedAt());
        if (entity.getHash() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getHash());
        }
      }
    };
    this.__preparedStmtOfUpdateRemark = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE image_data SET remark = ?, updatedAt = ? WHERE hash = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePath = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE image_data SET lastKnownPath = ?, updatedAt = ? WHERE hash = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByHash = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM image_data WHERE hash = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ImageData imageData, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfImageData.insert(imageData);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object delete(final ImageData imageData, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfImageData.handle(imageData);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final ImageData imageData, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfImageData.handle(imageData);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateRemark(final String hash, final String remark, final long updatedAt,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRemark.acquire();
        int _argIndex = 1;
        if (remark == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, remark);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        if (hash == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, hash);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateRemark.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object updatePath(final String hash, final String path, final long updatedAt,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePath.acquire();
        int _argIndex = 1;
        if (path == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, path);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        if (hash == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, hash);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePath.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object deleteByHash(final String hash, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByHash.acquire();
        int _argIndex = 1;
        if (hash == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, hash);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByHash.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getByHash(final String hash, final Continuation<? super ImageData> arg1) {
    final String _sql = "SELECT * FROM image_data WHERE hash = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hash == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hash);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ImageData>() {
      @Override
      @Nullable
      public ImageData call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
          final int _cursorIndexOfLastKnownPath = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ImageData _result;
          if (_cursor.moveToFirst()) {
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpRemark;
            if (_cursor.isNull(_cursorIndexOfRemark)) {
              _tmpRemark = null;
            } else {
              _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
            }
            final String _tmpLastKnownPath;
            if (_cursor.isNull(_cursorIndexOfLastKnownPath)) {
              _tmpLastKnownPath = null;
            } else {
              _tmpLastKnownPath = _cursor.getString(_cursorIndexOfLastKnownPath);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ImageData(_tmpHash,_tmpRemark,_tmpLastKnownPath,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<ImageData> getByHashFlow(final String hash) {
    final String _sql = "SELECT * FROM image_data WHERE hash = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hash == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hash);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"image_data"}, new Callable<ImageData>() {
      @Override
      @Nullable
      public ImageData call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
          final int _cursorIndexOfLastKnownPath = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ImageData _result;
          if (_cursor.moveToFirst()) {
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpRemark;
            if (_cursor.isNull(_cursorIndexOfRemark)) {
              _tmpRemark = null;
            } else {
              _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
            }
            final String _tmpLastKnownPath;
            if (_cursor.isNull(_cursorIndexOfLastKnownPath)) {
              _tmpLastKnownPath = null;
            } else {
              _tmpLastKnownPath = _cursor.getString(_cursorIndexOfLastKnownPath);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ImageData(_tmpHash,_tmpRemark,_tmpLastKnownPath,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ImageData>> getAllImages() {
    final String _sql = "SELECT * FROM image_data ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"image_data"}, new Callable<List<ImageData>>() {
      @Override
      @NonNull
      public List<ImageData> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
          final int _cursorIndexOfLastKnownPath = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ImageData> _result = new ArrayList<ImageData>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImageData _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpRemark;
            if (_cursor.isNull(_cursorIndexOfRemark)) {
              _tmpRemark = null;
            } else {
              _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
            }
            final String _tmpLastKnownPath;
            if (_cursor.isNull(_cursorIndexOfLastKnownPath)) {
              _tmpLastKnownPath = null;
            } else {
              _tmpLastKnownPath = _cursor.getString(_cursorIndexOfLastKnownPath);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ImageData(_tmpHash,_tmpRemark,_tmpLastKnownPath,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ImageData>> getImagesWithRemarks() {
    final String _sql = "SELECT * FROM image_data WHERE remark != '' ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"image_data"}, new Callable<List<ImageData>>() {
      @Override
      @NonNull
      public List<ImageData> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
          final int _cursorIndexOfLastKnownPath = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ImageData> _result = new ArrayList<ImageData>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImageData _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpRemark;
            if (_cursor.isNull(_cursorIndexOfRemark)) {
              _tmpRemark = null;
            } else {
              _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
            }
            final String _tmpLastKnownPath;
            if (_cursor.isNull(_cursorIndexOfLastKnownPath)) {
              _tmpLastKnownPath = null;
            } else {
              _tmpLastKnownPath = _cursor.getString(_cursorIndexOfLastKnownPath);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ImageData(_tmpHash,_tmpRemark,_tmpLastKnownPath,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ImageData>> searchByRemark(final String query) {
    final String _sql = "SELECT * FROM image_data WHERE remark LIKE '%' || ? || '%' ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"image_data"}, new Callable<List<ImageData>>() {
      @Override
      @NonNull
      public List<ImageData> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
          final int _cursorIndexOfLastKnownPath = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ImageData> _result = new ArrayList<ImageData>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImageData _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpRemark;
            if (_cursor.isNull(_cursorIndexOfRemark)) {
              _tmpRemark = null;
            } else {
              _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
            }
            final String _tmpLastKnownPath;
            if (_cursor.isNull(_cursorIndexOfLastKnownPath)) {
              _tmpLastKnownPath = null;
            } else {
              _tmpLastKnownPath = _cursor.getString(_cursorIndexOfLastKnownPath);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ImageData(_tmpHash,_tmpRemark,_tmpLastKnownPath,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
