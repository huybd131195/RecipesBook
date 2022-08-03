package com.codefresher.recipesbook.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.recipesbook.AddUserFragment
import com.codefresher.recipesbook.MainActivity2
import com.codefresher.recipesbook.Model.Users
import com.codefresher.recipesbook.R
import com.codefresher.recipesbook.UpdateUserFragment
import com.codefresher.recipesbook.adapter.UserAdapter
import com.codefresher.recipesbook.databinding.FragmentCreateBinding
import com.google.firebase.database.*


class CreateFragment : Fragment() {
    private var createBinding: FragmentCreateBinding? = null
    private val binding get() = createBinding!!
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")
    lateinit var userAdapter: UserAdapter
    val userList = ArrayList<Users>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createBinding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.floatingButton.setOnClickListener {
            val addUser = AddUserFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.mainContainer, addUser, AddUserFragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             val id =  userAdapter.getUserId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()
                Toast.makeText(context,"The user was deleted",Toast.LENGTH_LONG).show()
            }

        }).attachToRecyclerView(createBinding?.recyclerView)

        retriveDataFromDatabase()
        return binding.root
    }

    fun retriveDataFromDatabase() {

        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (eachUser in snapshot.children) {
                    val user = eachUser.getValue(Users::class.java)
                    if (user != null) {
                        println("userId: ${user.userId}")
                        println("userName: ${user.userName}")
                        println("userAge: ${user.userAge}")
                        println("userEmail: ${user.userEmail}")
                        println("******************")

                        userList.add(user)
                    }


                    userAdapter = UserAdapter(this@CreateFragment, userList) {
                        val another = UpdateUserFragment()
                        val transaction: FragmentTransaction =
                            requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.mainContainer, another)
                            .addToBackStack(null)
                        transaction.commit()


                    }
                    createBinding?.recyclerView?.layoutManager = LinearLayoutManager(context)
                    createBinding?.recyclerView?.adapter = userAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        createBinding = null
    }
}