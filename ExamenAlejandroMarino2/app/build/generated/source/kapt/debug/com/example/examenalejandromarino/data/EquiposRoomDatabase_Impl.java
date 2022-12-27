package com.example.examenalejandromarino.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EquiposRoomDatabase_Impl extends EquiposRoomDatabase {
  private volatile DaoEquipo _daoEquipo;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `equipos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `nacionalidad` TEXT NOT NULL, `puesto` INTEGER NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_equipos_nombre` ON `equipos` (`nombre`)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_equipos_puesto` ON `equipos` (`puesto`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `componentes` (`nombre` TEXT NOT NULL, `tipo` TEXT NOT NULL, `id_equipo` INTEGER NOT NULL, PRIMARY KEY(`nombre`), FOREIGN KEY(`id_equipo`) REFERENCES `equipos`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f249a859ab933b91c6c73c8f8d26102c')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `equipos`");
        _db.execSQL("DROP TABLE IF EXISTS `componentes`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsEquipos = new HashMap<String, TableInfo.Column>(4);
        _columnsEquipos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEquipos.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEquipos.put("nacionalidad", new TableInfo.Column("nacionalidad", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEquipos.put("puesto", new TableInfo.Column("puesto", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEquipos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEquipos = new HashSet<TableInfo.Index>(2);
        _indicesEquipos.add(new TableInfo.Index("index_equipos_nombre", true, Arrays.asList("nombre"), Arrays.asList("ASC")));
        _indicesEquipos.add(new TableInfo.Index("index_equipos_puesto", true, Arrays.asList("puesto"), Arrays.asList("ASC")));
        final TableInfo _infoEquipos = new TableInfo("equipos", _columnsEquipos, _foreignKeysEquipos, _indicesEquipos);
        final TableInfo _existingEquipos = TableInfo.read(_db, "equipos");
        if (! _infoEquipos.equals(_existingEquipos)) {
          return new RoomOpenHelper.ValidationResult(false, "equipos(com.example.examenalejandromarino.data.modelo.EquipoEntity).\n"
                  + " Expected:\n" + _infoEquipos + "\n"
                  + " Found:\n" + _existingEquipos);
        }
        final HashMap<String, TableInfo.Column> _columnsComponentes = new HashMap<String, TableInfo.Column>(3);
        _columnsComponentes.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComponentes.put("tipo", new TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComponentes.put("id_equipo", new TableInfo.Column("id_equipo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysComponentes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysComponentes.add(new TableInfo.ForeignKey("equipos", "NO ACTION", "NO ACTION",Arrays.asList("id_equipo"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesComponentes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoComponentes = new TableInfo("componentes", _columnsComponentes, _foreignKeysComponentes, _indicesComponentes);
        final TableInfo _existingComponentes = TableInfo.read(_db, "componentes");
        if (! _infoComponentes.equals(_existingComponentes)) {
          return new RoomOpenHelper.ValidationResult(false, "componentes(com.example.examenalejandromarino.data.modelo.ComponenteEntity).\n"
                  + " Expected:\n" + _infoComponentes + "\n"
                  + " Found:\n" + _existingComponentes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f249a859ab933b91c6c73c8f8d26102c", "cc83d700b41a65a875275ef5402156e6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "equipos","componentes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `componentes`");
      _db.execSQL("DELETE FROM `equipos`");
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
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DaoEquipo.class, DaoEquipo_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public DaoEquipo daoEquipo() {
    if (_daoEquipo != null) {
      return _daoEquipo;
    } else {
      synchronized(this) {
        if(_daoEquipo == null) {
          _daoEquipo = new DaoEquipo_Impl(this);
        }
        return _daoEquipo;
      }
    }
  }
}
