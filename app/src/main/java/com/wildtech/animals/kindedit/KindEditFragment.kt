package com.wildtech.animals.kindedit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.wildtech.animals.R
import com.wildtech.animals.databinding.FragmentKindEditBinding
import com.wildtech.animals.di.viewmodel.ViewModelFactory
import com.wildtech.animals.utils.Constant
import com.wildtech.animals.utils.Utils
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class KindEditFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: KindEditViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kind_edit, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(KindEditViewModel::class.java)


        DataBindingUtil.bind<FragmentKindEditBinding>(view)
            .apply {
                this!!.lifecycleOwner = this@KindEditFragment
                this.model = viewModel
            }

        viewModel.apply {

            observableStatus.observe(this@KindEditFragment, Observer { status ->
                status?.let { checkCondition(status, view) }
            })

            clickToSave.observe(this@KindEditFragment, Observer {
                if (it) saveEditDialog()
                else showMessage(view)
            })
        }

        initToolbar()

        return view
    }

    private fun showMessage(view:View) {
        Utils.showSnack(view, resources.getString(R.string.enter_name))
    }

    private fun checkCondition(status: Boolean, view: View) {
        if (status) {
            Utils.showSnack(view, resources.getString(R.string.saved))
        } else {
            Utils.showSnack(view, resources.getString(R.string.error))
        }
    }


    private fun initToolbar() {
        (context as DaggerAppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.edit)
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getKind(arguments!!.getInt(Constant.kindId))
    }

    private fun saveEditDialog() {

        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(resources.getString(R.string.save))
            .setMessage(resources.getString(R.string.save_kind))
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.edit()
        }

        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigate(R.id.kindListFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


}
