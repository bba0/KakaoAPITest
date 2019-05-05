package com.kakaoapitest.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakaoapitest.R
import com.kakaoapitest.data.model.Document

class DocumentAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val ITEM = 1

    val mDocumentList = ArrayList<Document>()
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

    override fun getItemCount(): Int = mDocumentList.size + 1


    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        when(p0) {
            is DocumentViewHolder -> {
                mDocumentList[position - 1].run {
                    p0.thumbnail.setImageURI(imageUrl)
                    p0.title.text = title
                    p0.name.text = name
                    p0.label.text = label
                    p0.date.text = dateToString
                }
            }
        }
    }

    fun addData(dataList: List<Document>) {
        mDocumentList.run {
            var startPosition = size + 1
            addAll(dataList)
            notifyItemRangeInserted(startPosition, dataList.size)
        }

    }

    fun clearData() {
        mDocumentList.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            ITEM
        }
    }

    class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: SimpleDraweeView = itemView.findViewById(R.id.thumbnail_simple_drawee_view)
        val label: TextView = itemView.findViewById(R.id.label_text_view)
        val name: TextView = itemView.findViewById(R.id.name_text_view)
        val title: TextView = itemView.findViewById(R.id.title_text_view)
        val date: TextView = itemView.findViewById(R.id.date_text_view)
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}