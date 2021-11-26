package com.example.avengers.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avengers.domain.model.Result
import kotlinx.android.synthetic.main.item_characters.view.*
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.avengers.R
import com.example.avengers.common.AppConstants
import com.example.avengers.common.listeners.OnLoadMoreListener
import com.example.avengers.domain.utils.setImage


class CharactersAdapter(
    private val mResults: ArrayList<Result>,
    private val mOnLoadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<CharactersAdapter.DataViewHolder>() {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result) {
            itemView.textViewCharacterName.text = result.name
            itemView.textViewCharacterDesc.text = result.description
            itemView.imageViewAvatar.setImage(itemView.context, result.thumbnail?.path+"."+result.thumbnail?.extension)

        if (adapterPosition == mResults.size - 2 && AppConstants.PAGINATION_LIMIT <= mResults.size){
            mOnLoadMoreListener.onLoadMore(adapterPosition)
        }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_characters, parent,
                false
            )
        )

    override fun getItemCount(): Int = mResults.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(mResults[position])

    fun addData(list: List<Result>) {
        mResults.addAll(list)
    }
}