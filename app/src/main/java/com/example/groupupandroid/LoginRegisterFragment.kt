package com.example.groupupandroid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.groupupandroid.databinding.ActivityMainBinding
import com.example.groupupandroid.databinding.FragmentLoginRegisterBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

///**
// * A simple [Fragment] subclass.
// * Use the [LoginRegisterFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
class LoginRegisterFragment : Fragment(){
    // Getting xml objects
    private lateinit var binding: FragmentLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginRegisterBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login_register, container, false)

        // If register button is tapped make register visible
        binding.registerToggleButton.setOnClickListener {
            binding.registerFields.visibility = View.VISIBLE
            binding.loginFields.visibility = View.GONE
        }

        // If login button is tapped make login visible
        binding.loginToggleButton.setOnClickListener {
            binding.registerFields.visibility = View.GONE
            binding.loginFields.visibility = View.VISIBLE
        }

        // If login button is pushed swap to maps
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.loginToHomeScreen)
        }

        // If register button is pushed swap to maps
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.loginToHomeScreen)
        }

        return view
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment loginRegisterFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            LoginRegisterFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}