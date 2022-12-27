package com.example.examenalejandromarino.data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0019\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u001f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/example/examenalejandromarino/data/EquipoRepository;", "", "daoEquipo", "Lcom/example/examenalejandromarino/data/DaoEquipo;", "(Lcom/example/examenalejandromarino/data/DaoEquipo;)V", "addComponente", "", "id", "", "componente", "Lcom/example/examenalejandromarino/domain/modelo/Componente;", "(ILcom/example/examenalejandromarino/domain/modelo/Componente;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addEquipo", "equipo", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "(Lcom/example/examenalejandromarino/domain/modelo/Equipo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEquipo", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getComponente", "nombre", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getComponentes", "", "Lcom/example/examenalejandromarino/data/modelo/ComponenteEntity;", "getEquipo", "getEquipos", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class EquipoRepository {
    private final com.example.examenalejandromarino.data.DaoEquipo daoEquipo = null;
    
    @javax.inject.Inject()
    public EquipoRepository(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.data.DaoEquipo daoEquipo) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEquipos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examenalejandromarino.domain.modelo.Equipo>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEquipo(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.examenalejandromarino.domain.modelo.Equipo> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteEquipo(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addEquipo(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.domain.modelo.Equipo equipo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addComponente(int id, @org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.domain.modelo.Componente componente, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getComponentes(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examenalejandromarino.data.modelo.ComponenteEntity>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getComponente(@org.jetbrains.annotations.NotNull()
    java.lang.String nombre, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}