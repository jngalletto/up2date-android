package com.mooveit.genesis.ui.common

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SingleItemAdapter<T, VH>(
    val context: Context,
    @LayoutRes val layout: Int,
    val factory: (View) -> VH
) : RecyclerView.Adapter<VH>() where T : Any, VH : SingleItemViewHolder<T> {
  var items = listOf<T>()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      factory(LayoutInflater.from(context).inflate(layout, parent, false))

  override fun onBindViewHolder(viewHolder: VH, position: Int) = viewHolder.bind(items[position])
}

abstract class SingleItemViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) where T : Any {
  abstract fun bind(item: T)
}
