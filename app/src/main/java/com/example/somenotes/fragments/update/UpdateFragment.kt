package com.example.somenotes.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.somenotes.R
import com.example.somenotes.model.Note
import com.example.somenotes.view_model.NoteViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNoteViewModel: NoteViewModel

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

       mNoteViewModel= ViewModelProvider(this).get(NoteViewModel::class.java)

       view.updateTitle_et.setText(args.currentNote.title)
       view.updateNote_et.setText(args.currentNote.content)

       view.update_btn.setOnClickListener {
            updateItem()
       }

       // Adding menu
       setHasOptionsMenu(true)

       return view
   }

    private fun updateItem() {
        val updatedTitle = updateTitle_et.text.toString()
        val updatedContent = updateNote_et.text.toString()

        if (inputCheck(updatedTitle, updatedContent)) {
            // Creating Note object
            val updatedNote = Note(args.currentNote.id, updatedTitle, updatedContent)
            // Updating Current Note
            mNoteViewModel.updateNote(updatedNote)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            // Navigating back
            findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(noteTitle: String, noteContent: String) : Boolean {
        return (noteTitle.isNotEmpty() && noteContent.isNotEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_btn) {
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(requireContext(), "Successfully removed the note.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
        }

        builder.setNegativeButton("No") {_, _ ->}

        builder.setTitle("Delete this note?")
        builder.setMessage("Are you sure, you want to delete this note?")

        builder.create().show()
    }

}