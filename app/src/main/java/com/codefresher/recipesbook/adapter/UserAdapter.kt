package com.codefresher.recipesbook.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.recipesbook.MainActivity2
import com.codefresher.recipesbook.Model.Users
import com.codefresher.recipesbook.R
import com.codefresher.recipesbook.UpdateUserFragment
import com.codefresher.recipesbook.databinding.FragmentCreateBinding
import com.codefresher.recipesbook.databinding.FragmentCreateItemBinding
import com.codefresher.recipesbook.fragment.CreateFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class UserAdapter(
    var context: CreateFragment,
    private var userList: ArrayList<Users>,
    val listener: (Users) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val adapterBinding: FragmentCreateItemBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = FragmentCreateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.adapterBinding.textViewName.text = userList[position].userName
        holder.adapterBinding.textViewAge.text = userList[position].userAge
        holder.adapterBinding.textViewEmail.text = userList[position].userEmail
        val imageUrl = userList[position].url
        Picasso.get().load(imageUrl).into(holder.adapterBinding.imageView, object : Callback {
            override fun onSuccess() {
                holder.adapterBinding.progressBar.visibility = View.INVISIBLE

            }

            override fun onError(e: Exception?) {
//          Toast.makeText(this@UserAdapter,e?.localizedMessage,Toast.LENGTH_SHORT).show()

            }

        })

        val linearLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.linearLayout)

        linearLayout.setOnClickListener {
            listener(Users())
            val tvN: EditText? = it.findViewById(R.id.edtName)
            val tvA: EditText? = it.findViewById(R.id.edtAge)
            val tvE: EditText? = it.findViewById(R.id.edtEmail)

            val inputN = tvN?.text.toString()
            val inputA = tvA?.text.toString()
            val inputE = tvE?.text.toString()
            val iD = userList[position].userId

            val bundle = Bundle()
            bundle.putString("name", inputN)
            bundle.putString("age", inputA)
            bundle.putString("email", inputE)
            bundle.putString("id", iD)
            val fragment = UpdateUserFragment()
            fragment.arguments = bundle

        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun getUserId(position: Int): String {
        return userList[position].userId
    }

}