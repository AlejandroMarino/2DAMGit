// Generated by Dagger (https://dagger.dev).
package com.example.examenalejandromarino.domain.usecases.equipo;

import com.example.examenalejandromarino.data.EquipoRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DeleteEquipo_Factory implements Factory<DeleteEquipo> {
  private final Provider<EquipoRepository> equipoRepositoryProvider;

  public DeleteEquipo_Factory(Provider<EquipoRepository> equipoRepositoryProvider) {
    this.equipoRepositoryProvider = equipoRepositoryProvider;
  }

  @Override
  public DeleteEquipo get() {
    return newInstance(equipoRepositoryProvider.get());
  }

  public static DeleteEquipo_Factory create(Provider<EquipoRepository> equipoRepositoryProvider) {
    return new DeleteEquipo_Factory(equipoRepositoryProvider);
  }

  public static DeleteEquipo newInstance(EquipoRepository equipoRepository) {
    return new DeleteEquipo(equipoRepository);
  }
}