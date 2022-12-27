package com.example.examenalejandromarino.data.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u001c\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0007\u00a8\u0006\r"}, d2 = {"Lcom/example/examenalejandromarino/data/di/RoomModule;", "", "()V", "getAssetDB", "", "providedDatabase", "Lcom/example/examenalejandromarino/data/EquiposRoomDatabase;", "context", "Landroid/content/Context;", "ruta", "providesDaoEquipos", "Lcom/example/examenalejandromarino/data/DaoEquipo;", "equiposRoomDatabase", "app_debug"})
@dagger.Module()
public final class RoomModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.examenalejandromarino.data.di.RoomModule INSTANCE = null;
    
    private RoomModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "assetDb")
    @dagger.Provides()
    public final java.lang.String getAssetDB() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.example.examenalejandromarino.data.EquiposRoomDatabase providedDatabase(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "assetDb")
    java.lang.String ruta) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.examenalejandromarino.data.DaoEquipo providesDaoEquipos(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.data.EquiposRoomDatabase equiposRoomDatabase) {
        return null;
    }
}