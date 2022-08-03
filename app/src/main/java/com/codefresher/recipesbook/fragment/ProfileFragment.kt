package com.codefresher.recipesbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.codefresher.recipesbook.R
import com.codefresher.recipesbook.databinding.FragmentProfileBinding
import com.google.firebase.database.*


class ProfileFragment : Fragment() {

    private var profileBinding: FragmentProfileBinding? = null
    private val binding get() = profileBinding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        profileBinding = null
    }
}