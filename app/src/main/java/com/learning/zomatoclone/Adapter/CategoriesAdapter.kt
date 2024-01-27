package com.learning.zomatoclone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.zomatoclone.Model.Categories.CatShown
import com.learning.zomatoclone.R
import com.learning.zomatoclone.databinding.CategoriesLayoutBinding

class CategoriesAdapter(
    private val items: List<CatShown>,
    private val context:Context
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoriesLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        Glide
            .with(context)
            .load(item.image)
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
    fun setOnClickListener(onClickListener: CategoriesAdapter.OnClickListener?) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: CatShown)
    }
    class ViewHolder(binding: CategoriesLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.catTextView
        val imageView = binding.catImaveView
    }
}