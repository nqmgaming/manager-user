package com.nqmgaming.roomandroid.fragment.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nqmgaming.roomandroid.R
import com.nqmgaming.roomandroid.model.User
import com.nqmgaming.roomandroid.viewmodel.UserViewModel

class AddFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<Button>(R.id.save_btn).setOnClickListener{
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val firstName = view?.findViewById<EditText>(R.id.first_name_et)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.last_name_et)?.text.toString()
        val age = view?.findViewById<EditText>(R.id.age_et)?.text.toString()

        if(inputCheck(firstName, lastName, age)){
            // Create User Object
            val user = User(0, firstName, lastName, Integer.parseInt(age))
            // Add Data to Database
            userViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))

    }


}