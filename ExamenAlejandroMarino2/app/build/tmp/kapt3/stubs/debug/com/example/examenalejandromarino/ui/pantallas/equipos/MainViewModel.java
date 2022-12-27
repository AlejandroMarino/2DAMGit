package com.example.examenalejandromarino.ui.pantallas.equipos;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0006\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u00020\u0010H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0017"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "stringProvider", "Lcom/example/examenalejandromarino/utils/StringProvider;", "getEquipos", "Lcom/example/examenalejandromarino/domain/usecases/equipo/GetEquipos;", "deleteEquipo", "Lcom/example/examenalejandromarino/domain/usecases/equipo/DeleteEquipo;", "(Lcom/example/examenalejandromarino/utils/StringProvider;Lcom/example/examenalejandromarino/domain/usecases/equipo/GetEquipos;Lcom/example/examenalejandromarino/domain/usecases/equipo/DeleteEquipo;)V", "_uiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainState;", "uiState", "Landroidx/lifecycle/LiveData;", "getUiState", "()Landroidx/lifecycle/LiveData;", "", "id", "", "handleEvent", "event", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent;", "loadEquipos", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.examenalejandromarino.utils.StringProvider stringProvider = null;
    private final com.example.examenalejandromarino.domain.usecases.equipo.GetEquipos getEquipos = null;
    private final com.example.examenalejandromarino.domain.usecases.equipo.DeleteEquipo deleteEquipo = null;
    private final androidx.lifecycle.MutableLiveData<com.example.examenalejandromarino.ui.pantallas.equipos.MainState> _uiState = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.utils.StringProvider stringProvider, @org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.domain.usecases.equipo.GetEquipos getEquipos, @org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.domain.usecases.equipo.DeleteEquipo deleteEquipo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.examenalejandromarino.ui.pantallas.equipos.MainState> getUiState() {
        return null;
    }
    
    public final void handleEvent(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.ui.pantallas.equipos.MainEvent event) {
    }
    
    private final void loadEquipos() {
    }
    
    private final void deleteEquipo(int id) {
    }
}