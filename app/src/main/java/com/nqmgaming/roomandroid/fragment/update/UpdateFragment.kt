package com.nqmgaming.roomandroid.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nqmgaming.roomandroid.R
import com.nqmgaming.roomandroid.viewmodel.UserViewModel

class UpdateFragment : Fragment() , MenuProvider {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<EditText>(R.id.update_first_name_et).setText(args.currentUser.firstName)
        view.findViewById<EditText>(R.id.update_last_name_et).setText(args.currentUser.lastName)
        view.findViewById<EditText>(R.id.update_age_et).setText(args.currentUser.age.toString())

        view.findViewById<Button>(R.id.update_btn).setOnClickListener {
            // update user
            updateItem()
        }

        // Add menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


        return view
    }

    private fun updateItem() {

        val firstName = view?.findViewById<EditText>(R.id.update_first_name_et)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.update_last_name_et)?.text.toString()
        val age = view?.findViewById<EditText>(R.id.update_age_et)?.text.toString().toInt()

        if (inputCheck(firstName, lastName, age)) {
            // update user
            val updatedUser = (args.currentUser.copy(firstName = firstName, lastName = lastName, age = age))
              userViewModel.updateUser(updatedUser)
            // show toast
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()

            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            // show toast
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: Int): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age < 0)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete_note) {
            deleteUser()
        }
        return true
    }

    private fun deleteUser() {
        // alert dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            userViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentUser.firstName}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()

    }


}