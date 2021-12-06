package com.example.marvels.common.listeners

import com.marvel.domain.model.CharacterModel

interface OnActionListener {

    fun onActionListener(position: Int, type: Int, characterDetail: CharacterModel)
}
