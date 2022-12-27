// Generated by Dagger (https://dagger.dev).
package com.example.examenalejandromarino.ui.pantallas.equipos;

import com.example.examenalejandromarino.domain.usecases.equipo.DeleteEquipo;
import com.example.examenalejandromarino.domain.usecases.equipo.GetEquipos;
import com.example.examenalejandromarino.utils.StringProvider;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<StringProvider> stringProvider;

  private final Provider<GetEquipos> getEquiposProvider;

  private final Provider<DeleteEquipo> deleteEquipoProvider;

  public MainViewModel_Factory(Provider<StringProvider> stringProvider,
      Provider<GetEquipos> getEquiposProvider, Provider<DeleteEquipo> deleteEquipoProvider) {
    this.stringProvider = stringProvider;
    this.getEquiposProvider = getEquiposProvider;
    this.deleteEquipoProvider = deleteEquipoProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(stringProvider.get(), getEquiposProvider.get(), deleteEquipoProvider.get());
  }

  public static MainViewModel_Factory create(Provider<StringProvider> stringProvider,
      Provider<GetEquipos> getEquiposProvider, Provider<DeleteEquipo> deleteEquipoProvider) {
    return new MainViewModel_Factory(stringProvider, getEquiposProvider, deleteEquipoProvider);
  }

  public static MainViewModel newInstance(StringProvider stringProvider, GetEquipos getEquipos,
      DeleteEquipo deleteEquipo) {
    return new MainViewModel(stringProvider, getEquipos, deleteEquipo);
  }
}