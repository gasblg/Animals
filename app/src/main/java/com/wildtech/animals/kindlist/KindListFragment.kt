package com.wildtech.animals.kindlist


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildtech.animals.R
import com.wildtech.animals.databinding.FragmentKindListBinding
import com.wildtech.animals.di.viewmodel.ViewModelFactory
import com.wildtech.animals.utils.ActionModeManager
import com.wildtech.animals.utils.Constant
import com.wildtech.animals.utils.Utils
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class KindListFragment : DaggerFragment(), ActionModeManager.Callback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var rootView: View
    private var isInActionMode = false
    private lateinit var viewModel: KindListViewModel
    private lateinit var adapter: KindAdapter
    private lateinit var recyclerView: RecyclerView
    private var listForDeleted: ArrayList<Int>? = null
    private val disposables = CompositeDisposable()
    private val actionModeManager by lazy {
        ActionModeManager(this)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_kind_list, container, false)
        initRecyclerView()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get<KindListViewModel>(KindListViewModel::class.java)

        DataBindingUtil.bind<FragmentKindListBinding>(rootView)
            .apply {
                this!!.lifecycleOwner = this@KindListFragment
                this.model = viewModel
            }

        viewModel.apply {
            observableStatus.observe(this@KindListFragment, Observer { status ->
                status?.let { checkCondition(status) }
            })
            clickFab.observe(this@KindListFragment, Observer {
                if (adapter.getSelectedItemCount() <= 0) {
                    Navigation.findNavController(rootView).navigate(R.id.kindAddFragment)

                }
            })
        }
        return rootView

    }

    override fun onResume() {
        super.onResume()

        disposables += viewModel.kindList
            .subscribeBy(
                onNext = {
                    adapter.submitList(it)
                },
                onError = {
                    Log.e(resources.getString(R.string.error), resources.getString(R.string.list_kind))
                })

        viewModel.observableState.observe(this@KindListFragment, Observer { state ->
            state?.let {
                if (state) {
                    markItemsForDelete()
                    isInActionMode = state
                }
            } ?: powerOffActionMode()

        })


    }

    private fun powerOffActionMode() {
        isInActionMode = false
    }

    private fun markItemsForDelete() {
        val list = viewModel.observableList.value
        if (list != null) {
            listForDeleted = list
            list.forEach {
                toggleSelection(it)
            }

        }

    }

    private fun checkCondition(status: Boolean) {
        if (status) {
            Utils.showSnack(rootView, resources.getString(R.string.deleted))
        } else {
            Utils.showSnack(rootView, resources.getString(R.string.not_deleted))
        }
    }

    private fun initRecyclerView() {
        recyclerView = rootView.findViewById<View>(R.id.rv) as RecyclerView
        adapter = KindAdapter(activity)
        recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        adapter.setOnItemLongClickListener { _, position ->
            toggleSelection(position)

        }
        adapter.setOnItemClickListener { kind, position ->
            if (adapter.getSelectedItemCount() > 0) {
                toggleSelection(position)

            } else {
                val bundle = Bundle()
                bundle.putInt(Constant.kindId, kind.id)
                Navigation.findNavController(rootView).navigate(R.id.kindEditFragment, bundle)
            }
        }


    }

    private fun toggleSelection(position: Int) {
        adapter.toggleSelection(position)
        actionModeManager.startMode()
    }


    override fun getCheckedKindsCount(): Int {
        return adapter.getSelectedItemCount()

    }

    override fun onStartSupportActionMode(callback: ActionMode.Callback): ActionMode? {
        val activity = (context as DaggerAppCompatActivity)
        isInActionMode = true
        return activity.startSupportActionMode(callback)
    }

    override fun onDeleteButtonClicked() {
        deleteItems()
    }

    override fun onActionModeDestroyed() {
        listForDeleted?.clear()
        adapter.clearSelections()
    }

    private fun deleteItems() {
        val selectedItemPositions = adapter.getSelectedItems()
        selectedItemPositions.forEach {
            viewModel.delete(it)
        }
        isInActionMode = false
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is DaggerAppCompatActivity) {
            context.supportActionBar?.apply {
                title = getString(R.string.app_name)
                setDisplayHomeAsUpEnabled(false)
                setHasOptionsMenu(true)
            }
        }
    }


    override fun onStop() {
        super.onStop()
        listForDeleted = adapter.getIntArrayList()
        viewModel.saveState(listForDeleted!!, isInActionMode)
        disposables.clear()
        adapter.clearSelections()
        isInActionMode = false
    }


}
