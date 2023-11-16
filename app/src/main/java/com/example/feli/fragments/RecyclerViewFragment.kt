package com.example.feli.fragments

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feli.MainActivity
import com.example.feli.Persona
import com.example.feli.R
import com.example.feli.adapters.UserAdapter
import com.example.feli.viewModel.RecyclerViewViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecyclerViewFragment : Fragment() {
    lateinit var v: View
    lateinit var recyclerView: RecyclerView
    lateinit var db: FirebaseFirestore
    lateinit var ButtonPlus:com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var progress: ProgressBar
    var url:MutableList<String> = mutableListOf("")
    var PersonaList: MutableList<Persona> = mutableListOf(Persona("", "", "", ""))

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private lateinit var viewModel: RecyclerViewViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        progress = v.findViewById(R.id.progressBar3)
        recyclerView = v.findViewById<RecyclerView>(R.id.feliRecyclerView)
        ButtonPlus = v.findViewById(R.id.floating_action_button)
        PersonaList.clear()
        url.clear()
        db = Firebase.firestore
        db.collection("FRIENDS")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    PersonaList.add(
                        Persona(
                            document.data["nombre"].toString(),
                            document.data["edad"].toString(),
                            document.data["descripcion"].toString(),
                            document.data["url"].toString()
                        )
                    )
                }
                progress.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                ButtonPlus.visibility = View.VISIBLE
                recyclerView.adapter =
                    UserAdapter(PersonaList, context = requireContext(), Onclick = {
                        var action =
                            RecyclerViewFragmentDirections.actionRecyclerViewFragmentToUserInfoFragment(
                                PersonaList[it],
                            )
                        findNavController().navigate(action)
                    })
                recyclerView.layoutManager = LinearLayoutManager(MainActivity())
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return v
    }

    override fun onStart() {
        recyclerView.adapter = UserAdapter(PersonaList, context = requireContext(), Onclick = {
            var action =
                RecyclerViewFragmentDirections.actionRecyclerViewFragmentToUserInfoFragment(
                    PersonaList[it],
                )
            v.findNavController().navigate(action)
        })

        ButtonPlus.setOnClickListener {
            var action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToAddPersonFragment()
            v.findNavController().navigate(action)
        }
        recyclerView.layoutManager = LinearLayoutManager(MainActivity())
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}