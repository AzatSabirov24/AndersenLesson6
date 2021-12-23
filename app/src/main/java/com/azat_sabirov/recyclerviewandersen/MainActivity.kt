package com.azat_sabirov.recyclerviewandersen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/*class MainActivity : AppCompatActivity(), ContactListFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContactListFragment()
    }

    private fun setContactListFragment() {
        val contactListFrag = ContactListFragment
        supportFragmentManager.beginTransaction().run {
            add(
                R.id.frag_container,
                contactListFrag.newInstance(),
                contactListFrag.TAG_CONTACT_LIST_FRAGMENT
            )
            commit()
        }
    }

    private fun setContactDetailsFragment(contact: Contact) {
        val contactDetailsFrag = ContactDetailFragment.newInstance(contact)

        supportFragmentManager.beginTransaction().run {
            add(R.id.frag_container, contactDetailsFrag, ContactDetailFragment.TAG_CONTACT_DETAILS_FRAG)
            addToBackStack(null)
            commit()
        }
    }

    override fun openDetailFragment(contact: Contact) {
        setContactDetailsFragment(contact)
    }
}*/

class MainActivity : AppCompatActivity(), ContactListFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContactListFragment()
    }

    private fun setContactListFragment() {
        val contactListFrag = ContactListFragment
        supportFragmentManager.beginTransaction().run {
            add(
                R.id.frag_container,
                contactListFrag.newInstance(),
                contactListFrag.TAG_CONTACT_LIST_FRAGMENT
            )
            commit()
        }
    }

    private fun setContactDetailsFragment(contact: Contact) {
        val contactDetailsFrag = ContactDetailFragment.newInstance(contact)

        supportFragmentManager.beginTransaction().run {
            add(R.id.frag_container, contactDetailsFrag, ContactDetailFragment.TAG_CONTACT_DETAILS_FRAG)
            addToBackStack(null)
            commit()
        }
    }

    override fun openDetailFragment(contact: Contact) {
        setContactDetailsFragment(contact)
    }
}
