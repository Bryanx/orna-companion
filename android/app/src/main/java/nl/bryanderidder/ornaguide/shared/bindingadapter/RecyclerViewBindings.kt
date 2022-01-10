package nl.bryanderidder.ornaguide.shared.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchListAdapter
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil


object RecyclerViewBindings {

    @JvmStatic
    @BindingAdapter("listAdapter", "listAdapterItems")
    fun <T, VH : RecyclerView.ViewHolder> bindListAdapter(
        view: RecyclerView,
        adapter: ListAdapter<T, VH>,
        items: List<T>?,
    ) {
        if (view.adapter == null)
            view.adapter = adapter
        (view.adapter as ListAdapter<T, VH>).submitList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("searchResultAdapter", "searchResultList")
    fun bindAdapterSearchResultList(
        view: RecyclerView,
        adapter: SearchListAdapter,
        items: List<SearchResult>?,
    ) {
        if (view.adapter == null)
            view.adapter = adapter
        (view.adapter as SearchListAdapter).submitList(items?.take(50)) {
            view.scrollToPosition(0)
        }
    }

    @JvmStatic
    @BindingAdapter("sameSizeDiscoverItems")
    fun bindSameSizeDiscoverItems(view: RecyclerView, sharedPrefsUtil: SharedPrefsUtil) {
        if (sharedPrefsUtil.isSameSizeDiscoverItems())
            view.layoutManager = GridLayoutManager(view.context, 2)
        else
            view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

}