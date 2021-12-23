package com.azat_sabirov.recyclerviewandersen

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ContactListFragment : Fragment() {
    private lateinit var rcView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private val contactsList = ArrayList<Contact>()
    private var listener: Listener? = null
    val displayList = ArrayList<Contact>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        contactAdapter = ContactAdapter(
            { contact -> listener?.openDetailFragment(contact) },
            displayList,
            requireActivity()
        )
        initViews(view)
    }

    private fun initViews(view: View) {
        val divider = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireActivity(), R.drawable.rc_view_divider)?.let {
            divider.setDrawable(it)
        }
        rcView = view.findViewById(R.id.rc_view)
        rcView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = contactAdapter
            addItemDecoration(divider)
        }
        try {
            val jsonObject = JSONObject(getJsonDataFromAsset("contacts.json"))
            val jsonArray = jsonObject.getJSONArray("contacts")
            for (i in 0 until jsonArray.length()) {
                val userData = jsonArray.getJSONObject(i)
                val contact = Contact(
                    userData.getInt("id"),
                    userData.getString("first_name"),
                    userData.getString("last_name"),
                    userData.getString("phone_number"),
                    userData.getString("avatar")
                )
                contactsList.add(contact)
            }
            displayList.addAll(contactsList)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getJsonDataFromAsset(fileName: String): String {
        var json = ""
        try {
            activity?.let {
                val inputStream = it.assets.open(fileName)
                val sizeOfFile = inputStream.available()
                val bufferData = ByteArray(sizeOfFile)
                inputStream.read(bufferData)
                inputStream.close()
                json = String(bufferData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        if (menuItem != null) {
            val searchNames = menuItem.actionView as SearchView
            searchNames.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { it ->
                        rcView.adapter?.apply {
                            if (it.isNotEmpty()) {
                                displayList.clear()
                                val search = it.lowercase(Locale.getDefault())
                                contactsList.forEach {
                                    if (it.name.lowercase(Locale.getDefault()).contains(search)) {
                                        displayList.add(it)
                                    }
                                }
                                notifyDataSetChanged()

                            } else {
                                displayList.clear()
                                displayList.addAll(contactsList)
                                notifyDataSetChanged()
                            }
                        }
                    }
                    return true
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    interface Listener {
        fun openDetailFragment(contact: Contact)
    }

    companion object {
        const val TAG_CONTACT_LIST_FRAGMENT = "TAG_CONTACT_LIST_FRAGMENT"

        fun newInstance() = ContactListFragment()
    }
}
