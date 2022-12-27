package com.example.examenalejandromarino.ui.pantallas.addEquipos;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0001\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposEvent;", "", "()V", "AddEquipo", "Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposEvent$AddEquipo;", "app_debug"})
public abstract class AddEquiposEvent {
    
    private AddEquiposEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposEvent$AddEquipo;", "Lcom/example/examenalejandromarino/ui/pantallas/addEquipos/AddEquiposEvent;", "equipo", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "(Lcom/example/examenalejandromarino/domain/modelo/Equipo;)V", "getEquipo", "()Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "app_debug"})
    public static final class AddEquipo extends com.example.examenalejandromarino.ui.pantallas.addEquipos.AddEquiposEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.example.examenalejandromarino.domain.modelo.Equipo equipo = null;
        
        public AddEquipo(@org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo equipo) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.examenalejandromarino.domain.modelo.Equipo getEquipo() {
            return null;
        }
    }
}