package com.azat_sabirov.recyclerviewandersen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContactAdapter(
    val callback: (Contact) -> Unit,
    private var contactsList: ArrayList<Contact>,
    private val context: Context
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private var newList = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(contactsList[position])
        holder.itemView.setOnClickListener {
            callback(contactsList[position])
        }
        holder.itemView.setOnLongClickListener {
            showPopupMenu(holder.itemView, position)
            true
        }
    }

    override fun getItemCount() = contactsList.size

    private fun removeItem(contact: Contact) {
        newList.addAll(contactsList)
        newList.remove(contact)
        notifyItemRangeChanged(0, contact.id)
        updateAdapter(newList)
        newList.clear()

    }

    private fun updateAdapter(newContacts: List<Contact>) {
        contactsList.apply {
            val diffResult = DiffUtil.calculateDiff(DiffUtilCallbacks(contactsList, newContacts))
            diffResult.dispatchUpdatesTo(this@ContactAdapter)
            clear()
            addAll(newContacts)
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    showAlertDialog(position)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showAlertDialog(position: Int) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Are You Sure?")
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                removeItem(contactsList[position])
            }
            .show()
    }

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        private val tvSurname = itemView.findViewById<TextView>(R.id.tv_surname)
        private val tvPhoneNumber = itemView.findViewById<TextView>(R.id.tv_phone_number)
        private val tvImage = itemView.findViewById<ImageView>(R.id.iv_contact)

        fun setData(contact: Contact) {
            tvName.text = contact.name
            tvSurname.text = contact.surname
            tvPhoneNumber.text = contact.phoneNumber

            Glide
                .with(context)
                .load(contact.image)
                .into(tvImage)
        }
    }
}
