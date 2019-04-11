package com.wildtech.animals.utils.diff

import androidx.recyclerview.widget.DiffUtil
import com.wildtech.animals.model.Kind

class KindDiffCallback: DiffUtil.ItemCallback<Kind>() {

    override fun areItemsTheSame(oldItem: Kind, newItem: Kind): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Kind, newItem: Kind): Boolean {
        return oldItem == newItem
    }
}