package com.wildtech.animals.utils

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import com.wildtech.animals.R

class ActionModeManager(private val callback: Callback) {

    private var mode: ActionMode? = null
    private var isActive = false


    fun startMode() {
        val checkedKindCount = callback.getCheckedKindsCount()
        if (checkedKindCount > 0 && !isActive) {
            mode = callback.onStartSupportActionMode(actionModeCallback)
        } else if (checkedKindCount == 0) {
            mode?.finish()
        }
          updateCountTitle(checkedKindCount)
    }

    private fun updateCountTitle(count: Int) {
        mode?.title=count.toString()
    }


    private val actionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_delete, menu)
            isActive = true
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            val id = item.itemId
            if (id == R.id.action_delete) {
                callback.onDeleteButtonClicked()
                mode.finish()
                return true
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            callback.onActionModeDestroyed()
            isActive = false

        }
    }

    interface Callback {

        fun getCheckedKindsCount(): Int

        fun onStartSupportActionMode(callback: ActionMode.Callback): ActionMode?

        fun onDeleteButtonClicked()

        fun onActionModeDestroyed()
    }
}