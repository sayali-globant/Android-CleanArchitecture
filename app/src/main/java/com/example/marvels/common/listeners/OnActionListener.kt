package com.example.marvels.common.listeners

import com.marvel.data.characters.model.CharacterDetail

interface OnActionListener {

    fun onActionListener(position: Int, type : Int, characterDetail: CharacterDetail)
}
