package com.example.feli.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.feli.R
import com.example.feli.viewModel.MainViewModel

class MainFragment : Fragment() {


    lateinit var v: View
    lateinit var BtnLogin: Button
    lateinit var BtnRegister: Button

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main, container, false)
        BtnLogin = v.findViewById(R.id.LoginBtn)
        BtnRegister = v.findViewById(R.id.RegisterBtn)
        return v
    }

    override fun onStart() {
        super.onStart()
        BtnLogin.setOnClickListener {
            var action = MainFragmentDirections.actionMainFragmentToLoginFragment()
            v.findNavController().navigate(action)
        }
        BtnRegister.setOnClickListener {
            var action = MainFragmentDirections.actionMainFragmentToRegisterFragment()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}