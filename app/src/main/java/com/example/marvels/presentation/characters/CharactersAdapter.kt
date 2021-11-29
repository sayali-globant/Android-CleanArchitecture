package com.example.marvels.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_characters.view.*

import com.example.marvels.R
import com.example.marvels.common.AppConstants
import com.example.marvels.common.listeners.OnActionListener
import com.example.marvels.data.entity.CharacterDetail
import com.example.marvels.domain.utils.setImage


class CharactersAdapter(
    private val mResults: ArrayList<CharacterDetail>,
    private val mOnActionListener: OnActionListener
) : RecyclerView.Adapter<CharactersAdapter.DataViewHolder>() {

    companion object {
        const val ACTION_LOAD_MORE = 1
        const val ACTION_ITEM_CLICK = 2
    }
    private val mPaginationStartsFromLast = 2

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(characterDetail: CharacterDetail) {
            itemView.textViewCharacterName.text = characterDetail.name
            itemView.textViewCharacterDesc.text = characterDetail.description
            itemView.imageViewAvatar.setImage(
                itemView.context,
                characterDetail.thumbnail?.path + "." + characterDetail.thumbnail?.extension
            )

            if (adapterPosition == mResults.size - mPaginationStartsFromLast && AppConstants.PAGINATION_LIMIT <= mResults.size) {
                mOnActionListener.onActionListener(
                    adapterPosition,
                    ACTION_LOAD_MORE,
                    characterDetail
                )
            }

            itemView.setOnClickListener {
                mOnActionListener.onActionListener(
                    adapterPosition,
                    ACTION_ITEM_CLICK, characterDetail
                )
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

    fun addData(list: List<CharacterDetail>) {
        mResults.addAll(list)
    }
}