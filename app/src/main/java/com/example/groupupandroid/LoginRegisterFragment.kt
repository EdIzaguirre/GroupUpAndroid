package com.example.groupupandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.groupupandroid.databinding.FragmentLoginRegisterBinding

class LoginRegisterFragment : Fragment(){
    // Getting xml objects
    private var binding: FragmentLoginRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginRegisterBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // If register button is tapped make register visible
        binding?.registerToggleButton?.setOnClickListener {
            binding?.registerFields?.visibility = View.VISIBLE
            binding?.loginFields?.visibility = View.GONE
        }

        // If login button is tapped make login visible
        binding?.loginToggleButton?.setOnClickListener {
            binding?.registerFields?.visibility = View.GONE
            binding?.loginFields?.visibility = View.VISIBLE
        }

        // If login button is pushed swap to maps
        binding?.loginButton?.setOnClickListener {
            findNavController().navigate(R.id.loginToHomeScreen)
        }

        // If register button is pushed swap to maps
        binding?.registerButton?.setOnClickListener {
            findNavController().navigate(R.id.loginToHomeScreen)
        }

        binding?.materialButtonToggleGroup?.check(binding?.loginToggleButton!!.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}