package com.codefresher.recipesbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codefresher.recipesbook.databinding.FragmentUpdateUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUserFragment : Fragment() {

    private var updateUserBinding: FragmentUpdateUserBinding? = null
    private val binding get() = updateUserBinding!!
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        updateUserBinding = FragmentUpdateUserBinding.inflate(inflater, container, false)

        getAndSetData()
        updateUserBinding?.btnUpdate?.setOnClickListener {
            updateData()
        }
        return binding.root
    }

    fun getAndSetData() {
        val name = arguments?.getString("name")
        val age = arguments?.getString("age")
        val email = arguments?.getString("email")

        updateUserBinding?.edtUpdateName?.setText(name)
        updateUserBinding?.edtUpdateAge?.setText(age)
        updateUserBinding?.edtUpdateEmail?.setText(email)
    }

    fun updateData() {
        val userId = arguments?.getString("id").toString()
        val updateName = updateUserBinding?.edtUpdateName?.text.toString()
        val updateAge = updateUserBinding?.edtUpdateAge?.text.toString()
        val updateEmail = updateUserBinding?.edtUpdateEmail?.text.toString()


        val userMap = mutableMapOf<String, Any>()
        userMap["userId"] = userId
        userMap["userName"] = updateName
        userMap["userAge"] = updateAge
        userMap["userEmail"] = updateEmail

        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                Toast.makeText(context, "The user has been updated", Toast.LENGTH_LONG).show()
                onDestroyView()
            }
        }
    }


}