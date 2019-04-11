package com.wildtech.animals.parent

import android.util.SparseBooleanArray
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(diffUtil) {

    internal var selectedItems: SparseBooleanArray = SparseBooleanArray()
    abstract fun toggleCheckedIcon(holder: RecyclerView.ViewHolder, position: Int)

    internal lateinit var onClick: ((item: T, position: Int) -> Unit)
    internal lateinit var onLongClick: ((item: T, position: Int) -> Unit)

    fun setOnItemClickListener(onClick: (item: T, position: Int) -> Unit) {
        this.onClick = onClick
    }

    fun setOnItemLongClickListener(onLongClick: (item: T, position: Int) -> Unit) {
        this.onLongClick = onLongClick
    }

    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position)

        } else {
            selectedItems.put(position, true)
        }

        notifyItemChanged(position)
    }


    fun clearSelections() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItemCount(): Int {
        return selectedItems.size()
    }

    fun getIntArrayList(): ArrayList<Int> {
        val intArray = arrayListOf<Int>()
        for (i in 0 until selectedItems.size()) {
            val index = selectedItems.keyAt(i)
            intArray.add(index)
        }
        return intArray
    }

    fun getSelectedItems(): List<T> {
        val items = ArrayList<T>(selectedItems.size())
        for (i in 0 until selectedItems.size()) {
            val index = selectedItems.keyAt(i)
            val item = getItem(index)
            items.add(item!!)
        }
        return items
    }

}