package com.example.feli.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.feli.Persona
import com.example.feli.R
import com.example.feli.viewModel.AddPersonViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPersonFragment : Fragment() {

    companion object {
        fun newInstance() = AddPersonFragment()
    }

    private lateinit var viewModel: AddPersonViewModel
    lateinit var v:View
    lateinit var EdtTxtNombre:com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtEdad:com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtCurso:com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtDescription:com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtUrl:com.google.android.material.textfield.TextInputLayout
    lateinit var ButtonConfirmar: Button
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_add_person, container, false)
        EdtTxtNombre = v.findViewById(R.id.nombreTxtField)
        EdtTxtEdad = v.findViewById(R.id.EdadTxtField)
        EdtTxtDescription = v.findViewById(R.id.DescripcionTxtField)
        EdtTxtUrl = v.findViewById(R.id.UrlTxtField)
        ButtonConfirmar = v.findViewById(R.id.buttonConfirmar)
        return v
    }

    override fun onStart() {
        ButtonConfirmar.setOnClickListener {
            var nombre = EdtTxtNombre.editText?.text?.toString()?.replace(Regex(" "), "") ?: ""
            var edad = EdtTxtEdad.editText?.text?.toString()?.replace(Regex(" "), "") ?: ""
            var Descripcion =
                EdtTxtDescription.editText?.text?.toString()?.replace(Regex(" "), "") ?: ""
            var Url =
                EdtTxtUrl.editText?.text?.toString()?.replace(Regex(" "), "") ?: ""
            var persona = Persona(nombre, edad, Descripcion, Url)
            db = Firebase.firestore
            db.collection("FRIENDS").document(nombre).set(persona)
            var action = AddPersonFragmentDirections.actionAddPersonFragmentToRecyclerViewFragment()
            v.findNavController().navigate(action)
        }
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPersonViewModel::class.java)
        // TODO: Use the ViewModel
    }


}