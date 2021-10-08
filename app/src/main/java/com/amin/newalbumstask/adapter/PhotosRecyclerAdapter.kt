package com.amin.newalbumstask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amin.newalbumstask.data.model.Photo
import com.amin.newalbumstask.databinding.ImageItemBinding
import com.amin.newalbumstask.utils.getImageUrlForGlide
import com.amin.newalbumstask.utils.setImageWithGlide

class PhotosRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {

        override fun areItemsTheSame(
            oldItem: Photo,
            newItem: Photo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Photo,
            newItem: Photo
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PhotosViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotosViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Photo>) {
        differ.submitList(list)
    }

    class PhotosViewHolder
    constructor(
        private val binding: ImageItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo) = with(binding.root) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }


            binding.imageIv.setImageWithGlide(
                binding.root.context,
                item.thumbnailUrl.getImageUrlForGlide()
            )

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Photo)
    }
}

