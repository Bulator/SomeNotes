package com.example.somenotes.fragments.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.somenotes.R
import com.example.somenotes.view_model.NoteViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager


class ListFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

      @SuppressLint("UseCompatLoadingForDrawables")
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

          // RecyclerView
          val adapter = ListAdapter()
          val recyclerView = view.recyclerview
          recyclerView.adapter = adapter
          recyclerView.layoutManager = LinearLayoutManager(requireContext())

          // NoteViewModel
          mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
          mNoteViewModel.readAllData.observe(viewLifecycleOwner, Observer { note ->
              adapter.setData(note)
          })

        view.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

          // Adding menu
          setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_btn) {
            deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        val positiveBtnText = R.string.positive_btn_txt
        builder.setPositiveButton("Yes") {_, _ ->
            mNoteViewModel.deleteAllNotes()
            Toast.makeText(requireContext(), "Successfully removed all notes.", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") {_, _ ->}

        builder.setTitle("Delete all notes?")

        builder.setMessage("Are you sure, you want to delete all notes?")
        builder.create().show()
    }


}