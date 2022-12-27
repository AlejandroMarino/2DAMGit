package com.example.examenalejandromarino.ui.pantallas.equipos;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent;", "", "()V", "DeleteEquipo", "LoadEquipos", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent$DeleteEquipo;", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent$LoadEquipos;", "app_debug"})
public abstract class MainEvent {
    
    private MainEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent$LoadEquipos;", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent;", "()V", "app_debug"})
    public static final class LoadEquipos extends com.example.examenalejandromarino.ui.pantallas.equipos.MainEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.examenalejandromarino.ui.pantallas.equipos.MainEvent.LoadEquipos INSTANCE = null;
        
        private LoadEquipos() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent$DeleteEquipo;", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/MainEvent;", "id", "", "(I)V", "getId", "()I", "app_debug"})
    public static final class DeleteEquipo extends com.example.examenalejandromarino.ui.pantallas.equipos.MainEvent {
        private final int id = 0;
        
        public DeleteEquipo(int id) {
            super();
        }
        
        public final int getId() {
            return 0;
        }
    }
}