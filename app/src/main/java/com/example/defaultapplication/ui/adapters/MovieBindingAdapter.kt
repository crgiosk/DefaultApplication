package com.example.defaultapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.ui.viewmodels.MovieBindingViewModel

class MovieBindingAdapter(var movieBindingViewModel: MovieBindingViewModel, var resource: Int): RecyclerView.Adapter<MovieBindingAdapter.CardCouponHolder>() {

    var coupons: MutableList<Movie> = mutableListOf()

    fun setData(coupons: MutableList<Movie>){
        this.coupons.clear()
        this.coupons.addAll(coupons)
        this.coupons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardCouponHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, p1, p0, false)
        return CardCouponHolder(binding)
    }

    override fun getItemCount(): Int {
        return coupons.size ?: 0
    }

    override fun onBindViewHolder(p0: CardCouponHolder, p1: Int) {
        p0.setDataCard(movieBindingViewModel, p1)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int{
        return resource
    }

    class CardCouponHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setDataCard(couponViewModel: MovieBindingViewModel, position: Int){
            binding?.setVariable(BR.movieViewModel, couponViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }


    }

}