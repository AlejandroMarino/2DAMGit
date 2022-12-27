package com.example.examenalejandromarino.ui.pantallas.equipos;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0003\u0012\u0013\u0014B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0015"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$EquiposViewHolder;", "actions", "Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$EquipoActions;", "(Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$EquipoActions;)V", "getActions", "()Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$EquipoActions;", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "DiffCallBack", "EquipoActions", "EquiposViewHolder", "app_debug"})
public final class EquiposAdapter extends androidx.recyclerview.widget.ListAdapter<com.example.examenalejandromarino.domain.modelo.Equipo, com.example.examenalejandromarino.ui.pantallas.equipos.EquiposAdapter.EquiposViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final com.example.examenalejandromarino.ui.pantallas.equipos.EquiposAdapter.EquipoActions actions = null;
    
    public EquiposAdapter(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.ui.pantallas.equipos.EquiposAdapter.EquipoActions actions) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examenalejandromarino.ui.pantallas.equipos.EquiposAdapter.EquipoActions getActions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.examenalejandromarino.ui.pantallas.equipos.EquiposAdapter.EquiposViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.examenalejandromarino.ui.pantallas.equipos.EquiposAdapter.EquiposViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$EquipoActions;", "", "onClickDelete", "", "equipo", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "onClickWatch", "app_debug"})
    public static abstract interface EquipoActions {
        
        public abstract void onClickDelete(@org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo equipo);
        
        public abstract void onClickWatch(@org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo equipo);
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$DiffCallBack;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    public static final class DiffCallBack extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.example.examenalejandromarino.domain.modelo.Equipo> {
        
        public DiffCallBack() {
            super();
        }
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo oldItem, @org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo oldItem, @org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter$EquiposViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/example/examenalejandromarino/ui/pantallas/equipos/EquiposAdapter;Landroid/view/View;)V", "binding", "Lcom/example/examenalejandromarino/databinding/ItemEquipoBinding;", "bind", "", "equipo", "Lcom/example/examenalejandromarino/domain/modelo/Equipo;", "app_debug"})
    public final class EquiposViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.example.examenalejandromarino.databinding.ItemEquipoBinding binding = null;
        
        public EquiposViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.example.examenalejandromarino.domain.modelo.Equipo equipo) {
        }
    }
}