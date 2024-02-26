package com.learning.zomatoclone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.zomatoclone.Model.InterestFragModel
import com.learning.zomatoclone.R
import com.learning.zomatoclone.databinding.InterestFragUiBinding

class InterestFragmentAdapter(
    private val items: List<InterestFragModel>,
    private val context:Context
) :
    RecyclerView.Adapter<InterestFragmentAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InterestFragUiBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
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
    fun setOnClickListener(onClickListener: InterestFragmentAdapter.OnClickListener?) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: InterestFragModel)
    }
    class ViewHolder(binding: InterestFragUiBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.nameFav
        val imageView = binding.imageFav
    }
}