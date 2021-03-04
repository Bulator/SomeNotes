package com.example.somenotes.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.somenotes.R
import com.example.somenotes.model.Note
import com.example.somenotes.view_model.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDb()
        }

        return view
    }

    private fun insertDataToDb() {
        val noteTitle = title_et.text.toString()
        val noteContent = note_et.text.toString()

        if (inputCheck(noteTitle, noteContent)) {
            // Creating note object
            val note = Note(0, noteTitle, noteContent)
            // Adding data to db
            mNoteViewModel.addNote(note)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please, fill out all fields.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(noteTitle: String, noteContent: String) : Boolean {
        return (noteTitle.isNotEmpty() && noteContent.isNotEmpty())
    }

}