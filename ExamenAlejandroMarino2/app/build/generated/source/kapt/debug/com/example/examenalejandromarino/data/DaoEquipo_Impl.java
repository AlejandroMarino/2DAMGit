package com.example.examenalejandromarino.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.examenalejandromarino.data.modelo.ComponenteEntity;
import com.example.examenalejandromarino.data.modelo.EquipoEntity;
import com.example.examenalejandromarino.data.modelo.EquipoWithComponente;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DaoEquipo_Impl implements DaoEquipo {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EquipoEntity> __insertionAdapterOfEquipoEntity;

  private final EntityInsertionAdapter<ComponenteEntity> __insertionAdapterOfComponenteEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEquipo;

  public DaoEquipo_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEquipoEntity = new EntityInsertionAdapter<EquipoEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `equipos` (`id`,`nombre`,`nacionalidad`,`puesto`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EquipoEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getNombre() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNombre());
        }
        if (value.getNacionalidad() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNacionalidad());
        }
        stmt.bindLong(4, value.getPuesto());
      }
    };
    this.__insertionAdapterOfComponenteEntity = new EntityInsertionAdapter<ComponenteEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `componentes` (`nombre`,`tipo`,`id_equipo`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ComponenteEntity value) {
        if (value.getNombre() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getNombre());
        }
        if (value.getTipo() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTipo());
        }
        stmt.bindLong(3, value.getId_equipo());
      }
    };
    this.__preparedStmtOfDeleteEquipo = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM equipos WHERE id = ? ";
        return _query;
      }
    };
  }

  @Override
  public Object addEquipo(final EquipoEntity equipo,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEquipoEntity.insert(equipo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object addComponente(final ComponenteEntity componente,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfComponenteEntity.insert(componente);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteEquipo(final int id, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEquipo.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteEquipo.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object getEquipos(final Continuation<? super List<EquipoWithComponente>> continuation) {
    final String _sql = "SELECT * FROM equipos";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EquipoWithComponente>>() {
      @Override
      public List<EquipoWithComponente> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfNacionalidad = CursorUtil.getColumnIndexOrThrow(_cursor, "nacionalidad");
          final int _cursorIndexOfPuesto = CursorUtil.getColumnIndexOrThrow(_cursor, "puesto");
          final LongSparseArray<ArrayList<ComponenteEntity>> _collectionComponentes = new LongSparseArray<ArrayList<ComponenteEntity>>();
          while (_cursor.moveToNext()) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
            ArrayList<ComponenteEntity> _tmpComponentesCollection = _collectionComponentes.get(_tmpKey);
            if (_tmpComponentesCollection == null) {
              _tmpComponentesCollection = new ArrayList<ComponenteEntity>();
              _collectionComponentes.put(_tmpKey, _tmpComponentesCollection);
            }
          }
          _cursor.moveToPosition(-1);
          __fetchRelationshipcomponentesAscomExampleExamenalejandromarinoDataModeloComponenteEntity(_collectionComponentes);
          final List<EquipoWithComponente> _result = new ArrayList<EquipoWithComponente>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final EquipoWithComponente _item;
            final EquipoEntity _tmpEquipo;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfNombre) && _cursor.isNull(_cursorIndexOfNacionalidad) && _cursor.isNull(_cursorIndexOfPuesto))) {
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final String _tmpNombre;
              if (_cursor.isNull(_cursorIndexOfNombre)) {
                _tmpNombre = null;
              } else {
                _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
              }
              final String _tmpNacionalidad;
              if (_cursor.isNull(_cursorIndexOfNacionalidad)) {
                _tmpNacionalidad = null;
              } else {
                _tmpNacionalidad = _cursor.getString(_cursorIndexOfNacionalidad);
              }
              final int _tmpPuesto;
              _tmpPuesto = _cursor.getInt(_cursorIndexOfPuesto);
              _tmpEquipo = new EquipoEntity(_tmpId,_tmpNombre,_tmpNacionalidad,_tmpPuesto);
            }  else  {
              _tmpEquipo = null;
            }
            ArrayList<ComponenteEntity> _tmpComponentesCollection_1 = null;
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
            _tmpComponentesCollection_1 = _collectionComponentes.get(_tmpKey_1);
            if (_tmpComponentesCollection_1 == null) {
              _tmpComponentesCollection_1 = new ArrayList<ComponenteEntity>();
            }
            _item = new EquipoWithComponente(_tmpEquipo,_tmpComponentesCollection_1);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getEquipo(final int id,
      final Continuation<? super EquipoWithComponente> continuation) {
    final String _sql = "SELECT * FROM equipos WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EquipoWithComponente>() {
      @Override
      public EquipoWithComponente call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfNacionalidad = CursorUtil.getColumnIndexOrThrow(_cursor, "nacionalidad");
          final int _cursorIndexOfPuesto = CursorUtil.getColumnIndexOrThrow(_cursor, "puesto");
          final LongSparseArray<ArrayList<ComponenteEntity>> _collectionComponentes = new LongSparseArray<ArrayList<ComponenteEntity>>();
          while (_cursor.moveToNext()) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
            ArrayList<ComponenteEntity> _tmpComponentesCollection = _collectionComponentes.get(_tmpKey);
            if (_tmpComponentesCollection == null) {
              _tmpComponentesCollection = new ArrayList<ComponenteEntity>();
              _collectionComponentes.put(_tmpKey, _tmpComponentesCollection);
            }
          }
          _cursor.moveToPosition(-1);
          __fetchRelationshipcomponentesAscomExampleExamenalejandromarinoDataModeloComponenteEntity(_collectionComponentes);
          final EquipoWithComponente _result;
          if(_cursor.moveToFirst()) {
            final EquipoEntity _tmpEquipo;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfNombre) && _cursor.isNull(_cursorIndexOfNacionalidad) && _cursor.isNull(_cursorIndexOfPuesto))) {
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final String _tmpNombre;
              if (_cursor.isNull(_cursorIndexOfNombre)) {
                _tmpNombre = null;
              } else {
                _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
              }
              final String _tmpNacionalidad;
              if (_cursor.isNull(_cursorIndexOfNacionalidad)) {
                _tmpNacionalidad = null;
              } else {
                _tmpNacionalidad = _cursor.getString(_cursorIndexOfNacionalidad);
              }
              final int _tmpPuesto;
              _tmpPuesto = _cursor.getInt(_cursorIndexOfPuesto);
              _tmpEquipo = new EquipoEntity(_tmpId,_tmpNombre,_tmpNacionalidad,_tmpPuesto);
            }  else  {
              _tmpEquipo = null;
            }
            ArrayList<ComponenteEntity> _tmpComponentesCollection_1 = null;
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
            _tmpComponentesCollection_1 = _collectionComponentes.get(_tmpKey_1);
            if (_tmpComponentesCollection_1 == null) {
              _tmpComponentesCollection_1 = new ArrayList<ComponenteEntity>();
            }
            _result = new EquipoWithComponente(_tmpEquipo,_tmpComponentesCollection_1);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getComponentes(final int id,
      final Continuation<? super List<ComponenteEntity>> continuation) {
    final String _sql = "SELECT * FROM componentes WHERE id_equipo = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ComponenteEntity>>() {
      @Override
      public List<ComponenteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfIdEquipo = CursorUtil.getColumnIndexOrThrow(_cursor, "id_equipo");
          final List<ComponenteEntity> _result = new ArrayList<ComponenteEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ComponenteEntity _item;
            final String _tmpNombre;
            if (_cursor.isNull(_cursorIndexOfNombre)) {
              _tmpNombre = null;
            } else {
              _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            }
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            final int _tmpId_equipo;
            _tmpId_equipo = _cursor.getInt(_cursorIndexOfIdEquipo);
            _item = new ComponenteEntity(_tmpNombre,_tmpTipo,_tmpId_equipo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getComponente(final String nombre, final Continuation<? super Unit> continuation) {
    final String _sql = "SELECT * FROM componentes WHERE nombre = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (nombre == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nombre);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Unit _result;
          if(_cursor.moveToFirst()) {
            _result = new Unit();
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcomponentesAscomExampleExamenalejandromarinoDataModeloComponenteEntity(
      final LongSparseArray<ArrayList<ComponenteEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<ComponenteEntity>> _tmpInnerMap = new LongSparseArray<ArrayList<ComponenteEntity>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipcomponentesAscomExampleExamenalejandromarinoDataModeloComponenteEntity(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<ComponenteEntity>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipcomponentesAscomExampleExamenalejandromarinoDataModeloComponenteEntity(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `nombre`,`tipo`,`id_equipo` FROM `componentes` WHERE `id_equipo` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id_equipo");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfNombre = 0;
      final int _cursorIndexOfTipo = 1;
      final int _cursorIndexOfIdEquipo = 2;
      while(_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_itemKeyIndex);
        ArrayList<ComponenteEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final ComponenteEntity _item_1;
          final String _tmpNombre;
          if (_cursor.isNull(_cursorIndexOfNombre)) {
            _tmpNombre = null;
          } else {
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
          }
          final String _tmpTipo;
          if (_cursor.isNull(_cursorIndexOfTipo)) {
            _tmpTipo = null;
          } else {
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
          }
          final int _tmpId_equipo;
          _tmpId_equipo = _cursor.getInt(_cursorIndexOfIdEquipo);
          _item_1 = new ComponenteEntity(_tmpNombre,_tmpTipo,_tmpId_equipo);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
