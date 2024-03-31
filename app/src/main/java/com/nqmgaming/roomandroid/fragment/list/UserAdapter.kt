package com.nqmgaming.roomandroid.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nqmgaming.roomandroid.R
import com.nqmgaming.roomandroid.model.User


class UserAdapter :RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList = emptyList<User>()

    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser: User = userList[position]
        holder.itemView.findViewById<TextView>(R.id.firstNameTextView).text = currentUser.firstName
        holder.itemView.findViewById<TextView>(R.id.lastNameTextView).text = currentUser.lastName
        holder.itemView.findViewById<TextView>(R.id.ageTextView).text = currentUser.age.toString()

        holder.itemView.findViewById<Button>(R.id.deleteButton).setOnClickListener {
            // delete user
        }

        holder.itemView.findViewById<CardView>(R.id.cardView).setOnClickListener {
            // update user
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentUser)
            it.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}
