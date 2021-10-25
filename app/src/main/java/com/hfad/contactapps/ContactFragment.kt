package com.hfad.contactapps


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.hfad.contactapps.contact.Contact
import com.hfad.contactapps.contact.ContactPickerProvider
import com.hfad.contactapps.contact.ContactProvider
import com.hfad.contactapps.databinding.FragmentContactBinding
import com.hfad.contactapps.permission.ContactPermissionController
import com.hfad.contactapps.permission.ContactPermissionFacade


class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private lateinit var contactPermissionController: ContactPermissionController
    private lateinit var contactProvider: ContactProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater, container, false)

        contactPermissionController = ContactPermissionFacade(requireActivity() as AppCompatActivity)
        contactProvider = ContactPickerProvider(requireActivity() as AppCompatActivity)

        contactPermissionController.register { isGranted ->
            if (isGranted) {
                contactProvider.request()
            } else {
                // toast
                Log.d("__TEST__", "isGranted: $isGranted")
            }
        }

        contactProvider.register { contact: Contact ->
            // viewModel.save
            Log.d("__TEST__", "contact: $contact")
        }
        
        binding.fab.setOnClickListener {
            contactPermissionController.request()
        }

        return binding.root
    }

}