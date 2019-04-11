package com.wildtech.animals.kindlist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.wildtech.animals.BR
import com.wildtech.animals.R
import com.wildtech.animals.model.Kind
import com.wildtech.animals.parent.AbstractAdapter
import com.wildtech.animals.utils.diff.KindDiffCallback
import kotlinx.android.synthetic.main.item_kind.view.*


class KindAdapter(private val context: FragmentActivity?) : AbstractAdapter<Kind>(KindDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as KindViewHolder
        val v = holder.bind(getItem(position))

        holder.itemView.setOnLongClickListener {
            onLongClick(getItem(holder.adapterPosition)!!, holder.adapterPosition)
            true
        }
        holder.itemView.setOnClickListener {
            onClick(getItem(holder.adapterPosition)!!, holder.adapterPosition)
        }

        toggleCheckedIcon(holder, holder.adapterPosition)

        return v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KindViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_kind, parent, false)

        return KindViewHolder(binding)

    }


    override fun toggleCheckedIcon(holder: RecyclerView.ViewHolder, position: Int) {
        if (selectedItems.get(position, false)) {
            holder.itemView.layout_kinds.setBackgroundColor(Color.BLACK)
        } else {
            holder.itemView.layout_kinds.setBackgroundColor(Color.WHITE)
        }
    }

    class KindViewHolder(private val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(kind: Kind?) {
            viewBinding.setVariable(BR.kind, kind)
            viewBinding.executePendingBindings()


        }

    }
}