package com.learning.zomatoclone.Adapter

import com.learning.zomatoclone.Model.Search.Meal
import com.learning.zomatoclone.databinding.SearchVyLetterBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.zomatoclone.R

class SearchByLetterAdapter(
    private val items: List<Meal>,
    private val context:Context
) :
    RecyclerView.Adapter<SearchByLetterAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchVyLetterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.strMeal
        Glide
            .with(context)
            .load(item.strMealThumb)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: Meal)
    }
    class ViewHolder(binding: SearchVyLetterBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.catTextView
        val imageView = binding.catImaveView
    }
}