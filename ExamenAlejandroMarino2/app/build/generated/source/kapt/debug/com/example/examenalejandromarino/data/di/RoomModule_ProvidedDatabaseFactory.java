// Generated by Dagger (https://dagger.dev).
package com.example.examenalejandromarino.data.di;

import android.content.Context;
import com.example.examenalejandromarino.data.EquiposRoomDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata({
    "dagger.hilt.android.qualifiers.ApplicationContext",
    "javax.inject.Named"
})
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RoomModule_ProvidedDatabaseFactory implements Factory<EquiposRoomDatabase> {
  private final Provider<Context> contextProvider;

  private final Provider<String> rutaProvider;

  public RoomModule_ProvidedDatabaseFactory(Provider<Context> contextProvider,
      Provider<String> rutaProvider) {
    this.contextProvider = contextProvider;
    this.rutaProvider = rutaProvider;
  }

  @Override
  public EquiposRoomDatabase get() {
    return providedDatabase(contextProvider.get(), rutaProvider.get());
  }

  public static RoomModule_ProvidedDatabaseFactory create(Provider<Context> contextProvider,
      Provider<String> rutaProvider) {
    return new RoomModule_ProvidedDatabaseFactory(contextProvider, rutaProvider);
  }

  public static EquiposRoomDatabase providedDatabase(Context context, String ruta) {
    return Preconditions.checkNotNullFromProvides(RoomModule.INSTANCE.providedDatabase(context, ruta));
  }
}