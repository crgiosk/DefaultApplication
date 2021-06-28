package com.mvs.androidsearchindex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.defaultapplication.R
import com.example.defaultapplication.entities.ContactResponse

/**
 * Created by Manjun on 17-Mar-18.
 */
class MainListAdapter(private val contentList: List<ContactResponse>, private val mContext: Context) : RecyclerView.Adapter<MainListAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val layoutInflater:LayoutInflater= LayoutInflater.from(mContext)
        val mView:View=layoutInflater.inflate(R.layout.row_item_word,parent,false)
        return  ContentViewHolder(mView)
    }

    fun getData(): List<ContactResponse> {
        return contentList
    }

    fun getItemByPosition(position: Int): ContactResponse {
        return contentList[position]
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = contentList[position]
        holder.textView.text= item.firstName.capitalize()
    }

    class ContentViewHolder(val initView: View) : RecyclerView.ViewHolder(initView) {
        internal var textView: TextView

        init {
            textView=initView.findViewById(R.id.tv_content)
        }
    }

}