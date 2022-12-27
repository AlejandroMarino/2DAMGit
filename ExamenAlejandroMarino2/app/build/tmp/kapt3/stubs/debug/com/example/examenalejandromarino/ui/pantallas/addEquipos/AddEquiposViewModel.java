package com.example.examenalejandromarino.ui.pantallas.addEquipos;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0002\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposViewModel;", "Landroidx/lifecycle/ViewModel;", "addEquipo", "Lcom/example/examenalejandromarino/domain/usecases/equipo/AddEquipo;", "(Lcom/example/examenalejandromarino/domain/usecases/equipo/AddEquipo;)V", "_uiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposState;", "uiState", "Landroidx/lifecycle/LiveData;", "getUiState", "()Landroidx/lifecycle/LiveData;", "", "equipo", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "handleEvent", "event", "Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposEvent;", "app_debug"})
public final class AddEquiposViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.examenalejandromarino.domain.usecases.equipo.AddEquipo addEquipo = null;
    private final androidx.lifecycle.MutableLiveData<com.example.examenalejandromarino.ui.pantallas.addEquipos.AddEquiposState> _uiState = null;
    
    @javax.inject.Inject()
    public AddEquiposViewModel(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.domain.usecases.equipo.AddEquipo addEquipo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.examenalejandromarino.ui.pantallas.addEquipos.AddEquiposState> getUiState() {
        return null;
    }
    
    public final void handleEvent(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.ui.pantallas.addEquipos.AddEquiposEvent event) {
    }
    
    private final void addEquipo(com.example.examenalejandromarino.domain.modelo.Equipo equipo) {
    }
}