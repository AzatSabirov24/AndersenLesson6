package com.azat_sabirov.recyclerviewandersen

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallbacks(
    private var oldContactsList: List<Contact>,
    private var newContactsList: List<Contact>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldContactsList.size

    override fun getNewListSize() = newContactsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContactsList[oldItemPosition].name == newContactsList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldContactsList[oldItemPosition]
        val newItem = newContactsList[newItemPosition]
        return oldItem == newItem
    }
}