package com.stackexchangelite.app.ui.adapter

import com.stackexchangelite.app.data.model.Item

interface QuestionsAdapterEvents {
    fun onItemSelect(item: Item)
}