// Generated by Dagger (https://dagger.dev).
package com.example.examenalejandromarino.data.di;

import com.example.examenalejandromarino.data.DaoEquipo;
import com.example.examenalejandromarino.data.EquiposRoomDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RoomModule_ProvidesDaoEquiposFactory implements Factory<DaoEquipo> {
  private final Provider<EquiposRoomDatabase> equiposRoomDatabaseProvider;

  public RoomModule_ProvidesDaoEquiposFactory(
      Provider<EquiposRoomDatabase> equiposRoomDatabaseProvider) {
    this.equiposRoomDatabaseProvider = equiposRoomDatabaseProvider;
  }

  @Override
  public DaoEquipo get() {
    return providesDaoEquipos(equiposRoomDatabaseProvider.get());
  }

  public static RoomModule_ProvidesDaoEquiposFactory create(
      Provider<EquiposRoomDatabase> equiposRoomDatabaseProvider) {
    return new RoomModule_ProvidesDaoEquiposFactory(equiposRoomDatabaseProvider);
  }

  public static DaoEquipo providesDaoEquipos(EquiposRoomDatabase equiposRoomDatabase) {
    return Preconditions.checkNotNullFromProvides(RoomModule.INSTANCE.providesDaoEquipos(equiposRoomDatabase));
  }
}