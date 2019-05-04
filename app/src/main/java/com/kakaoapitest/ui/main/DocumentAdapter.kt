package com.kakaoapitest.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakaoapitest.R

class DocumentAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val ITEM = 1
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return when (type) {
            HEADER -> {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_sort_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_document, parent, false)
                DocumentViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = 100 + 1


    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            ITEM
        }
    }

    class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}