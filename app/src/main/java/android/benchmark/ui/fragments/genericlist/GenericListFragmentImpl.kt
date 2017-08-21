package android.benchmark.ui.fragments.genericlist

import android.benchmark.R
import android.benchmark.helpers.Services
import android.benchmark.helpers.dataservices.datasource.DataSourceId
import android.benchmark.helpers.dataservices.datasource.ObservableDataSource
import android.benchmark.ui.fragments.base.BaseFragment
import android.benchmark.ui.fragments.base.FragmentConfiguration
import android.benchmark.ui.fragments.base.ToolbarConfiguration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.generic_view.*
import java.io.Serializable

class GenericListFragmentImpl : BaseFragment<GenericPresenter>(), GenericListFragment {
    private val dataSourceContainer = Services.instance.dataSourceContainer
    private val eventBusContainer = Services.instance.eventBusContainer
    private var eventClickId : Serializable? = null

    init {
        presenter = GenericPresenter(this)
        configuration = FragmentConfiguration.withLayout(R.layout.generic_view).showBackArrow().create()
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        val configurationArg = args?.get(GenericListFragment.TOOLBAR_CONFIGURATION) as ToolbarConfiguration?

        if (configurationArg != null){
            this.configuration.toolbar.titleResourceId = configurationArg.titleResourceId
            this.configuration.toolbar.showBackArrow = configurationArg.showBackArrow
        }

        val dataSourceId = args?.get(GenericListFragment.DATA_SOURCE_ID) as DataSourceId?
        eventClickId = args?.get(GenericListFragment.EVENT_CLICK_ID) as Serializable?

        val mapperClassName = args?.get(GenericListFragment.MAPPER_CLASS_NAME) as String?

        dataSourceId?.let {
            val dataSource = dataSourceContainer.getDataSource(it)

            if (dataSource != null && dataSource.isObservableDataSource()) {
                var observableDataSource = dataSource as ObservableDataSource<*>

                observableDataSource?.let {
                    if (mapperClassName != null){
                        val mapperInstance = Class.forName(mapperClassName).newInstance()
                        if (mapperInstance is GenericItemMap){
                            presenter?.items = mapperInstance.map(it.data.observable)
                        }
                    }
                    else {
                        val items = observableDataSource.data.observable as Observable<GenericItem<*>>?
                        if (items != null){
                            presenter?.items = items
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        recyclerView?.let { rv ->
            presenter?.let {
                it.items?.let {
                    val list = it.toList().blockingGet()
                    if (list != null) {
                        rv.adapter = GenericListAdapter(list) { item ->

                            if (item != null) {
                                eventClickId?.let { ds ->
                                    val eventBus = eventBusContainer.get<GenericItemClickEvent>(ds)
                                    eventBus.post(GenericItemClickEvent(item))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView?.let { rv ->
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(context)
        }
    }
}
