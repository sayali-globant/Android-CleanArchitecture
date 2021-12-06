package com.example.marvels.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvels.R
import com.example.marvels.common.listeners.OnActionListener
import com.example.marvels.databinding.ItemCharactersBinding
import com.marvel.domain.model.CharacterModel


class CharactersAdapter(
    private val mResults: ArrayList<CharacterModel>,
    private val mOnActionListener: OnActionListener
) : RecyclerView.Adapter<CharactersAdapter.DataViewHolder>() {

    companion object {
        const val ACTION_LOAD_MORE = 1
        const val ACTION_ITEM_CLICK = 2
    }

    inner class DataViewHolder(private val binding: ItemCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(characterDetail: CharacterModel) {
            binding.characterDetail = characterDetail
            binding.imageUrl =
                characterDetail.thumbnail?.path + "." + characterDetail.thumbnail?.extension

            binding.container.setOnClickListener {
                mOnActionListener.onActionListener(
                    adapterPosition,
                    ACTION_ITEM_CLICK, characterDetail
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding: ItemCharactersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_characters, parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = mResults.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(mResults[position])
    }

    fun addData(list: List<CharacterModel>) {
        mResults.addAll(list)
    }
}
