package com.kakaoapitest.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakaoapitest.R
import com.kakaoapitest.data.model.Document

class DocumentAdapter(var onItemClick: (Document) -> Unit, var onSortItemSelect: () -> Unit, var onApiTypeSelect: (String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        when (p0) {
            is DocumentViewHolder -> {
                mDocumentList[position - 1].run {
                    p0.thumbnail.setImageURI(imageUrl)
                    p0.title.text = title
                    p0.name.text = name
                    p0.label.text = label
                    p0.date.text = dateToString
                    p0.itemView.setOnClickListener {
                        onItemClick(this)
                    }
                    p0.itemView.setBackgroundResource(
                        if (isOpen) {
                            R.color.dimmed
                        } else {
                            R.color.white
                        }
                    )
                }
            }
            is HeaderViewHolder -> {
                p0.apiTypeTextView.run {
                    setAdapter(p0.mAutoCompleteAdapter)
                    threshold = 1
                    setOnTouchListener { _, _ ->
                        showDropDown()
                        return@setOnTouchListener false
                    }
                    setOnItemClickListener { parent, _, position, _ ->
                        var type = parent.getItemAtPosition(position) as String
                        p0.mAutoCompleteAdapter.clear()
                        if (type == "All") {
                            p0.mAutoCompleteAdapter.add("Blog")
                            p0.mAutoCompleteAdapter.add("Cafe")
                        } else if (type == "Blog") {
                            p0.mAutoCompleteAdapter.add("All")
                            p0.mAutoCompleteAdapter.add("Cafe")
                        } else {
                            p0.mAutoCompleteAdapter.add("All")
                            p0.mAutoCompleteAdapter.add("Blog")
                        }
                        onApiTypeSelect(type)
                    }
                }
                p0.sortButton.setOnClickListener {
                    onSortItemSelect()
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
        val apiTypeTextView: AutoCompleteTextView = itemView.findViewById(R.id.api_type_text_view)
        var mAutoCompleteAdapter =
            ArrayAdapter(itemView.context, android.R.layout.simple_list_item_1, arrayListOf("Blog", "Cafe"))
        var sortButton: Button = itemView.findViewById(R.id.sort_button)
    }

}