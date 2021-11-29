package com.example.marvels.common.listeners

import com.example.marvels.data.entity.CharacterDetail

interface OnActionListener {

    fun onActionListener(position: Int, type : Int, characterDetail: CharacterDetail)
}