package com.sample.imagesearch.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sample.imagesearch.R
import com.sample.imagesearch.model.ImageDataModel


class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var imageDataList : List<ImageDataModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageDataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvImageTitle.text ="Title: "+ imageDataList.get(position).title
        holder.tvImagehex.text = "Hex: "+imageDataList.get(position).hex
        Glide.with(context).load(imageDataList.get(position).imageUrl)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
        if(imageDataList.get(position).isLiked) {
            holder.heartBtn.setImageResource(R.drawable.like)
        } else {
            holder.heartBtn.setImageResource(R.drawable.unlike)
        }
        holder.heartBtn.setOnClickListener(View.OnClickListener {
            if (imageDataList.get(position).isLiked) {
                imageDataList.get(position).isLiked = false
                holder.heartBtn.setImageResource(R.drawable.unlike)

            } else {
                imageDataList.get(position).isLiked = true
                holder.heartBtn.setImageResource(R.drawable.like)
            }
        })

        holder.image.setOnClickListener(View.OnClickListener {
            val url = imageDataList.get(position).imageUrl
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        })
    }

    fun setImageListItems(imageList: List<ImageDataModel>){
        imageDataList = imageList;
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val tvImageTitle: AppCompatTextView = itemView!!.findViewById(R.id.txt_title)
        val image: ImageView = itemView!!.findViewById(R.id.img_url)
        val tvImagehex : AppCompatTextView = itemView!!.findViewById(R.id.txt_hex)
        val heartBtn : ImageView = itemView!!.findViewById(R.id.heart_btn)

    }
}