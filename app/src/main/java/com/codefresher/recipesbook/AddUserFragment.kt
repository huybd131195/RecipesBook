package com.codefresher.recipesbook

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codefresher.recipesbook.Model.Users
import com.codefresher.recipesbook.databinding.FragmentAddUserBinding
import com.codefresher.recipesbook.databinding.FragmentProfileBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.FirebaseStorageKtxRegistrar
import com.squareup.picasso.Picasso
import java.net.URL
import java.util.*

class AddUserFragment : Fragment() {
    private var addUserBinding: FragmentAddUserBinding? = null
    private val binding get() = addUserBinding!!
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myReference: DatabaseReference = database.reference.child("MyUsers")
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var imageUri: Uri? = null
    private val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = firebaseStorage.reference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addUserBinding = FragmentAddUserBinding.inflate(inflater, container, false)


        //register
        registerActivityResult()
        addUserBinding?.btnAddUser?.setOnClickListener {
            uploadPhoto()

        }
        addUserBinding?.imageProfile?.setOnClickListener {
            chooseImage()

        }


        return binding.root

    }


    fun registerActivityResult() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                val resultCode = result.resultCode
                val imgData = result.data

                if (resultCode == RESULT_OK && imgData != null) {
                    imageUri = imgData.data

                    imageUri?.let {

                        Picasso.get().load(it).into(addUserBinding?.imageProfile)
                    }
                }

            })
    }

    fun chooseImage() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {

        }
    }


    private fun addUserToDatabase(url: String) {
        val id: String = myReference.push().key.toString()
        val name: String = addUserBinding?.edtName?.text.toString()
        val age: String = addUserBinding?.edtAge?.text.toString()
        val email: String = addUserBinding?.edtEmail?.text.toString()


        val user = Users(id, name, age, email, url)

        myReference.child(id).setValue(user).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    "The new user has been added to database", Toast.LENGTH_LONG

                ).show()
                addUserBinding?.btnAddUser?.isClickable = true
                addUserBinding?.progressBarAddUser?.visibility = View.INVISIBLE

            } else {
                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_LONG).show()
            }

        }

    }

    fun uploadPhoto() {
        addUserBinding?.btnAddUser?.isClickable = false
        addUserBinding?.progressBarAddUser?.visibility = View.VISIBLE

        val imageName = UUID.randomUUID().toString()
        val imgReference = storageReference.child("images").child("user photo")

        imageUri?.let { uri ->

            imgReference.putFile(uri).addOnCompleteListener {

                Toast.makeText(context, "Image uploaded", Toast.LENGTH_LONG).show()

                val myUploadedImageReference = storageReference.child("images").child(imageName)
                myUploadedImageReference.downloadUrl.addOnCompleteListener { url ->

                    val imageURL = url.toString()
                    addUserToDatabase(imageURL)

                }
            }.addOnFailureListener {
                Toast.makeText(context,it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }
    }

}