package com.example.somenotes.fragments.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.somenotes.R
import com.example.somenotes.model.Note
import com.example.somenotes.view_model.NoteViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var noteList = emptyList<Note>()

    private lateinit var mNoteViewModel: NoteViewModel

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.itemView.noteTitle_tv.text = currentItem.title
        holder.itemView.noteContent_tv.text = currentItem.content

        // Delete btn on list of notes
//        mNoteViewModel= ViewModelProvider().get(NoteViewModel::class.java)
//        holder.itemView.delete_btn.setOnClickListener {
//            Log.d("TAG", "${holder.itemView.noteTitle_tv.text} note's delete btn was tapped.")
//        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment2(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(note: List<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }

}