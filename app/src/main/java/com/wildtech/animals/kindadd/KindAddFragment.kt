package com.wildtech.animals.kindadd


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.wildtech.animals.R
import com.wildtech.animals.databinding.FragmentKindAddBinding
import com.wildtech.animals.di.viewmodel.ViewModelFactory
import com.wildtech.animals.utils.Utils
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class KindAddFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: KindAddViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kind_add, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(KindAddViewModel::class.java)

        DataBindingUtil.bind<FragmentKindAddBinding>(view)
            .apply {
                this!!.lifecycleOwner = this@KindAddFragment
                this.model = viewModel
            }

        viewModel.observableStatus.observe(this, Observer { status ->
            status?.let { checkCondition(status, view) }

        })

        initToolbar()

        return view
    }

    private fun initToolbar() {
        (context as DaggerAppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.add)
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }
    }

    private fun checkCondition(status: Boolean, view: View) {
        if (status) {
            Utils.showToast(context!!.applicationContext, resources.getString(R.string.created_kind))
            navController.navigate(R.id.kindListFragment)
        } else {
            Utils.showSnack(view, resources.getString(R.string.enter_name))
        }
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
