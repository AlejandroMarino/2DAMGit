package com.example.examenalejandromarino.data.modelo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0011\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u00c6\u0003J%\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u001e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/example/examenalejandromarino/data/modelo/EquipoWithComponente;", "", "equipo", "Lcom/example/examenalejandromarino/data/modelo/EquipoEntity;", "componentes", "", "Lcom/example/examenalejandromarino/data/modelo/ComponenteEntity;", "(Lcom/example/examenalejandromarino/data/modelo/EquipoEntity;Ljava/util/List;)V", "getComponentes", "()Ljava/util/List;", "getEquipo", "()Lcom/example/examenalejandromarino/data/modelo/EquipoEntity;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class EquipoWithComponente {
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Embedded()
    private final com.example.examenalejandromarino.data.modelo.EquipoEntity equipo = null;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Relation(parentColumn = "id", entityColumn = "id_equipo")
    private final java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity> componentes = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examenalejandromarino.data.modelo.EquipoWithComponente copy(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.data.modelo.EquipoEntity equipo, @org.jetbrains.annotations.Nullable()
    java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity> componentes) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public EquipoWithComponente(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.data.modelo.EquipoEntity equipo, @org.jetbrains.annotations.Nullable()
    java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity> componentes) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examenalejandromarino.data.modelo.EquipoEntity component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examenalejandromarino.data.modelo.EquipoEntity getEquipo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity> getComponentes() {
        return null;
    }
}