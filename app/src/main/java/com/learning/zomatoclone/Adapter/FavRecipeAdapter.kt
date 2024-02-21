package com.learning.zomatoclone.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.learning.zomatoclone.Model.FavRecipeModel
import com.learning.zomatoclone.R
import com.learning.zomatoclone.databinding.FavUiBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide

class FavRecipeAdapter(
    private val items: List<FavRecipeModel>,
    private val context: Context
) :
    RecyclerView.Adapter<FavRecipeAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FavUiBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.cat.text=item.cat
        holder.area.text=item.area
        Glide
            .with(context)
            .load(item.imagr)
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
        fun onClick(position: Int, model: FavRecipeModel)
    }
    class ViewHolder(binding: FavUiBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.nameFav
        val imageView = binding.imageFav
        val cat= binding.catFav
        val area=binding.areaFav
    }
}