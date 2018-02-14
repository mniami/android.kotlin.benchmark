package android.benchmark.ui.fragments.genericlist

import android.androidkotlinbenchmark.R
import android.benchmark.domain.Privilege
import android.benchmark.domain.User
import android.benchmark.helpers.Services
import android.benchmark.helpers.dataservices.datasource.DataSourceId
import android.benchmark.helpers.dataservices.datasource.ObservableDataSource
import android.benchmark.ui.fragments.base.BaseFragment
import android.benchmark.ui.fragments.base.FragmentConfiguration
import android.benchmark.ui.fragments.base.ToolbarConfiguration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.generic_view.*
import java.io.Serializable

class GenericListFragmentImpl : BaseFragment<GenericPresenter>(), GenericListFragment {
    private val dataSourceContainer = Services.instance.dataSourceContainer
    private val eventBusContainer = Services.instance.eventBusContainer
    private var eventClickId: Serializable? = null
    private val database = Services.instance.database
    private var currentUser = User()
    private var itemMap : GenericItemMap = EmptyGenericItemMap()
    private val mapperInstanceProvider = Services.instance.mapperInstanceProvider

    init {
        presenter = GenericPresenter(this)
        configuration = FragmentConfiguration.withLayout(R.layout.generic_view).showBackArrow().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        loadArguments()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.string.menu_refresh -> {
                swipeRefresh.isRefreshing = true
                refreshAdapter()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        database.getCurrentUserAsync()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = { user ->
                    currentUser = user

                    if (user.person.privilege == Privilege.ADMIN) {
                        tbAdmin.visibility = View.VISIBLE
                    }
                    actionButton.visibility = View.VISIBLE
                    initAdapter()
                })
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView?.let { rv ->
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(context)
        }
        actionButton?.setOnClickListener {
            itemMap.addItem()
        }
        swipeRefresh?.setOnRefreshListener {
            refreshAdapter()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView?

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                val genericAdapter = recyclerView?.adapter as GenericListAdapter?
                if (text != null) {
                    genericAdapter?.filter(text)
                }
                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }
        })
    }

    private fun initAdapter() {
        if (recyclerView?.adapter == null) {
            refreshAdapter()
        }
    }

    private fun refreshAdapter() {
        presenter?.items?.observeOn(AndroidSchedulers.mainThread())?.toList()?.onErrorReturn({ listOf() })?.subscribeBy(
                onError = {
                    Toast.makeText(this@GenericListFragmentImpl.context, "Ups", Toast.LENGTH_SHORT)
                },
                onSuccess = { list ->
                    recyclerView?.adapter = GenericListAdapter(list) { item ->
                        val lEventClickId = eventClickId
                        if (item != null && lEventClickId != null) {
                            val eventBus = eventBusContainer.get<GenericItemClickEvent>(lEventClickId)
                            eventBus.post(GenericItemClickEvent(item))
                        }
                    }
                    swipeRefresh?.isRefreshing = false
                })
    }

    private fun loadArguments() {
        val toolbarConfiguration = arguments?.get(GenericListFragment.TOOLBAR_CONFIGURATION)
        val dataSourceId = arguments?.get(GenericListFragment.DATA_SOURCE_ID)
        val mapperClassName = arguments?.get(GenericListFragment.MAPPER_CLASS_NAME)
        val eventClickId = arguments?.get(GenericListFragment.EVENT_CLICK_ID)

        if (toolbarConfiguration is ToolbarConfiguration) {
            this.configuration.toolbar.titleResourceId = toolbarConfiguration.titleResourceId
            this.configuration.toolbar.showBackArrow = toolbarConfiguration.showBackArrow
        }

        if (dataSourceId is DataSourceId && mapperClassName is String && eventClickId is Serializable) {
            val dataSource = dataSourceContainer.getDataSource(dataSourceId)

            if (dataSource != null && dataSource.isObservableDataSource() && dataSource is ObservableDataSource<*>) {
                if (mapperClassName != null) {
                    val mapperInstance = mapperInstanceProvider.get<GenericItemMap>(mapperClassName)
                    if (mapperInstance != null) {
                        itemMap = mapperInstance
                        presenter?.items = itemMap.map(dataSource.data.observable)
                    }
                } else {
                    val items = dataSource.data.observable as Observable<GenericItem<*>>?
                    if (items != null) {
                        presenter?.items = items
                    }
                }
            }
        }
    }
}

