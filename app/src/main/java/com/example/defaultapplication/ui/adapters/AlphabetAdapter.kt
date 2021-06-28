package com.mvs.androidsearchindex


import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.defaultapplication.R
import com.example.defaultapplication.entities.Alphabet

/**
 * Created by User on 17-Mar-18.
 */
class AlphabetAdapter(private val mContext: Context, val alphaClickListener: (Alphabet) -> Unit) : RecyclerView.Adapter<AlphabetAdapter.MyViewHolder>() {

    private val alphabetList: MutableList<Alphabet> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val mView = layoutInflater.inflate(R.layout.row_item_layout, parent, false)
        return MyViewHolder(mView)
    }

    fun setData(data: MutableList<Alphabet>){
        this.alphabetList.clear()
        this.alphabetList.addAll(data)
        notifyDataSetChanged()
    }

    fun getData(): MutableList<Alphabet>{
        return alphabetList
    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }

    private fun setAllElementsFalse(char: Char){
        alphabetList.forEach { it.isChecked = it.char == char }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = alphabetList[position]
        holder.textView.text= item.char.toString()
        holder.textView.setOnClickListener {
            alphaClickListener(item)
            setAllElementsFalse(item.char)
            rowIndex = position
            this.notifyDataSetChanged()
        }

        if(item.isChecked) {
            holder.textView.textSize=25F
            holder.textView.typeface= Typeface.DEFAULT_BOLD
        } else {
            holder.textView.textSize=15F
            holder.textView.typeface= Typeface.DEFAULT
        }
    }

    fun getSelected(): Alphabet? {
        return alphabetList.find { it.isChecked }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var textView: TextView = itemView.findViewById(R.id.tv_word)
    }

    companion object {
        private var rowIndex: Int=-1
    }

}