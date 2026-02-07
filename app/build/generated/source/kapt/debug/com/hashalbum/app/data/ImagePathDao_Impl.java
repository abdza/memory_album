package com.hashalbum.app.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
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
public final class ImagePathDao_Impl implements ImagePathDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ImagePath> __insertionAdapterOfImagePath;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastSeen;

  private final SharedSQLiteStatement __preparedStmtOfUpdateValidity;

  private final SharedSQLiteStatement __preparedStmtOfDeletePath;

  private final SharedSQLiteStatement __preparedStmtOfDeleteStaleInvalidPaths;

  public ImagePathDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfImagePath = new EntityInsertionAdapter<ImagePath>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `image_paths` (`hash`,`path`,`lastSeen`,`isValid`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ImagePath entity) {
        if (entity.getHash() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getHash());
        }
        if (entity.getPath() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPath());
        }
        statement.bindLong(3, entity.getLastSeen());
        final int _tmp = entity.isValid() ? 1 : 0;
        statement.bindLong(4, _tmp);
      }
    };
    this.__preparedStmtOfUpdateLastSeen = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE image_paths SET lastSeen = ? WHERE hash = ? AND path = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateValidity = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE image_paths SET isValid = ? WHERE hash = ? AND path = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePath = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM image_paths WHERE hash = ? AND path = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteStaleInvalidPaths = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM image_paths WHERE isValid = 0 AND lastSeen < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPath(final ImagePath imagePath,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfImagePath.insert(imagePath);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastSeen(final String hash, final String path, final long lastSeen,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastSeen.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lastSeen);
        _argIndex = 2;
        if (hash == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, hash);
        }
        _argIndex = 3;
        if (path == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, path);
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
          __preparedStmtOfUpdateLastSeen.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateValidity(final String hash, final String path, final boolean isValid,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateValidity.acquire();
        int _argIndex = 1;
        final int _tmp = isValid ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (hash == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, hash);
        }
        _argIndex = 3;
        if (path == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, path);
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
          __preparedStmtOfUpdateValidity.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePath(final String hash, final String path,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePath.acquire();
        int _argIndex = 1;
        if (hash == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, hash);
        }
        _argIndex = 2;
        if (path == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, path);
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
          __preparedStmtOfDeletePath.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteStaleInvalidPaths(final long cutoffTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteStaleInvalidPaths.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoffTime);
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
          __preparedStmtOfDeleteStaleInvalidPaths.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ImagePath>> getPathsForHash(final String hash) {
    final String _sql = "SELECT * FROM image_paths WHERE hash = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hash == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hash);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"image_paths"}, new Callable<List<ImagePath>>() {
      @Override
      @NonNull
      public List<ImagePath> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final List<ImagePath> _result = new ArrayList<ImagePath>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImagePath _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsValid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp != 0;
            _item = new ImagePath(_tmpHash,_tmpPath,_tmpLastSeen,_tmpIsValid);
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
  public Object getPathsForHashSync(final String hash,
      final Continuation<? super List<ImagePath>> $completion) {
    final String _sql = "SELECT * FROM image_paths WHERE hash = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hash == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hash);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ImagePath>>() {
      @Override
      @NonNull
      public List<ImagePath> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final List<ImagePath> _result = new ArrayList<ImagePath>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImagePath _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsValid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp != 0;
            _item = new ImagePath(_tmpHash,_tmpPath,_tmpLastSeen,_tmpIsValid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ImagePath>> getAllPaths() {
    final String _sql = "SELECT * FROM image_paths";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"image_paths"}, new Callable<List<ImagePath>>() {
      @Override
      @NonNull
      public List<ImagePath> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final List<ImagePath> _result = new ArrayList<ImagePath>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImagePath _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsValid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp != 0;
            _item = new ImagePath(_tmpHash,_tmpPath,_tmpLastSeen,_tmpIsValid);
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
  public Object getAllPathsSync(final Continuation<? super List<ImagePath>> $completion) {
    final String _sql = "SELECT * FROM image_paths";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ImagePath>>() {
      @Override
      @NonNull
      public List<ImagePath> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfHash = CursorUtil.getColumnIndexOrThrow(_cursor, "hash");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfLastSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeen");
          final int _cursorIndexOfIsValid = CursorUtil.getColumnIndexOrThrow(_cursor, "isValid");
          final List<ImagePath> _result = new ArrayList<ImagePath>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ImagePath _item;
            final String _tmpHash;
            if (_cursor.isNull(_cursorIndexOfHash)) {
              _tmpHash = null;
            } else {
              _tmpHash = _cursor.getString(_cursorIndexOfHash);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final long _tmpLastSeen;
            _tmpLastSeen = _cursor.getLong(_cursorIndexOfLastSeen);
            final boolean _tmpIsValid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsValid);
            _tmpIsValid = _tmp != 0;
            _item = new ImagePath(_tmpHash,_tmpPath,_tmpLastSeen,_tmpIsValid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
