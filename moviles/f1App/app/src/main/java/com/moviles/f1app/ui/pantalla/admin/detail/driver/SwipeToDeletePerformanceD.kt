package com.moviles.f1app.ui.pantalla.admin.detail.driver

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.moviles.f1app.ui.pantalla.admin.detail.race.PerformanceAdapterRace

class SwipeToDeletePerformanceD(var adapter: PerformanceAdapterDriver) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val performance = adapter.currentList[position]
        adapter.actions.onClickDelete(performance)
    }
}
