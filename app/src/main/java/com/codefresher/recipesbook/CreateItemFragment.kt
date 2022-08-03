package com.codefresher.recipesbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefresher.recipesbook.databinding.FragmentAddUserBinding
import com.codefresher.recipesbook.databinding.FragmentCreateItemBinding


class CreateItemFragment : Fragment() {
    private var  createItemBinding: FragmentCreateItemBinding? = null
    private val binding get() = createItemBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        createItemBinding = FragmentCreateItemBinding.inflate(inflater, container, false)

        val args = this.arguments
        val inputDataN = args?.get("name")
        val inputDataA = args?.get("age")
        val inputDataE = args?.get("email")

        createItemBinding!!.textViewName.text = inputDataN.toString()
        createItemBinding!!.textViewAge.text = inputDataA.toString()
        createItemBinding!!.textViewEmail.text = inputDataE.toString()


        return binding.root
    }


}