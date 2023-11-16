package com.example.feli.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.feli.Persona
import com.example.feli.R
import com.example.feli.viewModel.EditViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditFragment : Fragment() {

    lateinit var v: View
    lateinit var EdtTxtNombre: com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtEdad: com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtCurso: com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtDescription: com.google.android.material.textfield.TextInputLayout
    lateinit var EdtTxtUrl: com.google.android.material.textfield.TextInputLayout
    lateinit var ButtonConfirmar: Button
    lateinit var userInfo: Persona
    lateinit var db: FirebaseFirestore

    companion object {
        fun newInstance() = EditFragment()
    }

    private lateinit var viewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit, container, false)
        EdtTxtNombre = v.findViewById(R.id.nombreTxtField)
        EdtTxtEdad = v.findViewById(R.id.EdadTxtField)
        EdtTxtDescription = v.findViewById(R.id.DescripcionTxtField)
        EdtTxtUrl = v.findViewById(R.id.UrlTxtField)
        ButtonConfirmar = v.findViewById(R.id.buttonConfirmar)
        userInfo = EditFragmentArgs.fromBundle(requireArguments()).userInfo
        return v
    }

    override fun onStart() {
        ButtonConfirmar.setOnClickListener {
            db = Firebase.firestore
            var url = EdtTxtUrl.editText?.text?.toString()?.replace(Regex(" "), "")!!
            var nombre = EdtTxtNombre.editText?.text?.toString()?.replace(Regex(" "), "")!!
            var Edad = EdtTxtEdad.editText?.text?.toString()?.replace(Regex(" "), "")!!
            var descripcion =
                EdtTxtDescription.editText?.text?.toString()?.replace(Regex(" "), "")!!
            if (EdtTxtNombre.editText?.text?.toString()?.replace(Regex(" "), "")
                    ?.isEmpty() == true
            ) {
                nombre =
                    userInfo.Nombre
            }
            if (EdtTxtEdad.editText?.text?.toString()?.replace(Regex(" "), "")?.isEmpty() == true) {
                Edad =
                    userInfo.Edad
            }
            if (EdtTxtDescription.editText?.text?.toString()?.replace(Regex(" "), "")
                    ?.isEmpty() == true
            ) {
                descripcion =
                    userInfo.Descripcion
            }

            if (EdtTxtUrl.editText?.text?.toString()?.replace(Regex(" "), "")?.isEmpty() == true) {
                url = userInfo.url

            }

            var persona = Persona(nombre, Edad, descripcion, url)

            db.collection("FRIENDS").document(nombre).set(persona).addOnSuccessListener {
                var action = EditFragmentDirections.actionEditFragmentToUserInfoFragment(userInfo)
                v.findNavController().navigate(action)
            }
        }
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}