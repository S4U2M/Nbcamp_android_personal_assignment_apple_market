package com.example.nbcamp_android_personal_assignment_apple_market.Main

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbcamp_android_personal_assignment_apple_market.DB.SellerModel
import com.example.nbcamp_android_personal_assignment_apple_market.DB.SellerDB
import com.example.nbcamp_android_personal_assignment_apple_market.databinding.ItemBinding
import java.util.Locale

class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    interface ItemLongClick{
        fun onLongClick(view:View,position: Int)
    }
    var itemClick : ItemClick? = null
    var itemLongClick : ItemLongClick? = null

    fun getItemChanged(item:Int,position: Int){

        SellerDB.sellerModelDB[position].like = item

        notifyItemChanged(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = SellerDB.sellerModelDB[position]

        holder.bind(item)

        holder.itemView.setOnClickListener {
         itemClick?.onClick(it,position)
        }

        holder.itemView.setOnLongClickListener(){
            itemLongClick?.onLongClick(it,position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return SellerDB.sellerModelDB.size
    }

    class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SellerModel) {
            with(binding) {
                itemImg.setImageResource(item.img)
                itemTitle.text = item.title
                itemAdress.text = item.homeAdress
                itemPrice.text = "${
                    NumberFormat.getNumberInstance(Locale.getDefault()).format(item.price)
                }원"
                itemLikeCount.text = "${item.like}"
                itemChatCount.text = "${item.chat}"
            }
        }
    }

}
