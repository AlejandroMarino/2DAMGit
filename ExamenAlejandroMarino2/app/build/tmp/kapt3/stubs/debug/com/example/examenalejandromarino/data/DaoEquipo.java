package com.example.examenalejandromarino.data;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u001f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0019\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0014H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/example/examenalejandromarino/data/DaoEquipo;", "", "addComponente", "", "componente", "Lcom/example/examenalejandromarino/data/modelo/ComponenteEntity;", "(Lcom/example/examenalejandromarino/data/modelo/ComponenteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addEquipo", "equipo", "Lcom/example/examenalejandromarino/data/modelo/EquipoEntity;", "(Lcom/example/examenalejandromarino/data/modelo/EquipoEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEquipo", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getComponente", "nombre", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getComponentes", "", "getEquipo", "Lcom/example/examenalejandromarino/data/modelo/EquipoWithComponente;", "getEquipos", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface DaoEquipo {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM equipos")
    public abstract java.lang.Object getEquipos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examenalejandromarino.data.modelo.EquipoWithComponente>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM equipos WHERE id = :id")
    public abstract java.lang.Object getEquipo(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.examenalejandromarino.data.modelo.EquipoWithComponente> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    public abstract java.lang.Object addEquipo(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.data.modelo.EquipoEntity equipo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "DELETE FROM equipos WHERE id = :id ")
    public abstract java.lang.Object deleteEquipo(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM componentes WHERE id_equipo = :id")
    public abstract java.lang.Object getComponentes(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM componentes WHERE nombre = :nombre")
    public abstract java.lang.Object getComponente(@org.jetbrains.annotations.NotNull()
    java.lang.String nombre, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    public abstract java.lang.Object addComponente(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.data.modelo.ComponenteEntity componente, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}