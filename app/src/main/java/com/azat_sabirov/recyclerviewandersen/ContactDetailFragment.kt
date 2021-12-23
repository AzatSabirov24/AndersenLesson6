package com.azat_sabirov.recyclerviewandersen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ContactDetailFragment : Fragment() {
    private lateinit var contact: Contact
    private lateinit var tvNameDetail: TextView
    private lateinit var tvSurnameDetail: TextView
    private lateinit var tvPhoneNumberDetail: TextView
    private lateinit var ivDetail: ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contact = requireArguments().getSerializable(EXTRA_CONTACT) as Contact
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setTextAndImageToViews()
    }

    private fun setTextAndImageToViews() {
        contact.apply {
            tvNameDetail.text = name
            tvSurnameDetail.text = surname
            tvPhoneNumberDetail.text = phoneNumber

            Glide
                .with(requireActivity())
                .load(image)
                .into(ivDetail)
        }
    }

    private fun initViews(view: View) {
        tvNameDetail = view.findViewById(R.id.tv_name_detail)
        tvSurnameDetail = view.findViewById(R.id.tv_surname_detail)
        tvPhoneNumberDetail = view.findViewById(R.id.tv_phone_number_detail)
        ivDetail = view.findViewById(R.id.iv_image_detail)
    }

    companion object {
        const val TAG_CONTACT_DETAILS_FRAG = "TAG_CONTACT_DETAILS_FRAG"
        const val EXTRA_CONTACT = "EXTRA_CONTACT"

        fun newInstance(contact: Contact) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_CONTACT, contact)
                }
            }
    }
}
