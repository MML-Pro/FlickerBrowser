package com.mml.flickerbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mml.flickerbrowser.databinding.BrowseBinding
import com.squareup.picasso.Picasso

class FlickerAdapter(private var photoList: List<Photo>) :

    RecyclerView.Adapter<FlickerAdapter.FlickerImageViewHolder>() {
    companion object {
        private const val TAG = "FlickerAdapter"
    }

    inner class FlickerImageViewHolder(val binding: BrowseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickerImageViewHolder {

        Log.d(TAG, "onCreateViewHolder new view reqested")


        return FlickerImageViewHolder(
            BrowseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun loadNewData(newPhoto: List<Photo>){
        photoList = newPhoto
        notifyDataSetChanged()
     }

    fun getPhoto(position:Int) : Photo?{

        return if (photoList.isNotEmpty()) photoList[position] else null
    }

    override fun onBindViewHolder(holder: FlickerImageViewHolder, position: Int) {

//        Log.d(TAG, "onBindViewHolder: ${photoItem.title} ---> $position")

        if(photoList.isEmpty()){
            holder.binding.thumbnail.setImageResource(R.drawable.placeholder)
            holder.binding.title.setText(R.string.empty_photo)
        }else {
            val photoItem:Photo = photoList[position]
            Picasso.get().load(photoItem.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.thumbnail)

            holder.binding.title.text = photoItem.title
        }



    }

    override fun getItemCount(): Int {
//        Log.d(TAG, "getItemCount: called")
        return if (photoList.isNotEmpty()) photoList.size else 1
    }
}