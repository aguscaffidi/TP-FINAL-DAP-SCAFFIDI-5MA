package com.example.feli.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.feli.Persona
import com.example.feli.R
import com.example.feli.viewModel.FeliInfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.*

var element: String = ""

class UserInfoFragment : Fragment() {


    lateinit var v: View
    lateinit var NameTxt: TextView
    lateinit var EdadTxt: TextView
    lateinit var DescriptionTxt: TextView
    lateinit var MainImageView: ImageView
    lateinit var EditarBtn: Button
    lateinit var EliminarBtn: Button
    lateinit var userInfo: Persona
    lateinit var db: FirebaseFirestore


    companion object {
        fun newInstance() = UserInfoFragment()
    }

    private lateinit var viewModel: FeliInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.user_info_fragment, container, false)
        db = Firebase.firestore
        NameTxt = v.findViewById(R.id.NameTxt)
        EdadTxt = v.findViewById(R.id.EdadTxt)
        DescriptionTxt = v.findViewById(R.id.DescriptionTxt)
        MainImageView = v.findViewById(R.id.MainImageView)
        userInfo = UserInfoFragmentArgs.fromBundle(requireArguments()).personaInfo
        EliminarBtn = v.findViewById(R.id.eliminarBtn)
        EditarBtn = v.findViewById(R.id.EditarBtn)
        return v
    }
    override fun onStart() {
        NameTxt.text = userInfo.Nombre
        element = NameTxt.text as String
        EdadTxt.text = "Edad: " + userInfo.Edad
        DescriptionTxt.text = userInfo.Descripcion
        Glide.with(this).load(userInfo.url).centerInside().circleCrop().into(MainImageView)
        EliminarBtn.setOnClickListener {
            db.collection("FRIENDS").document(userInfo.Nombre).delete()
            var action = UserInfoFragmentDirections.actionUserInfoFragmentToRecyclerViewFragment()
            v.findNavController().navigate(action)
        }
        EditarBtn.setOnClickListener {
            var action = UserInfoFragmentDirections.actionUserInfoFragmentToEditFragment(userInfo)
            v.findNavController().navigate(action)
        }
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeliInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

