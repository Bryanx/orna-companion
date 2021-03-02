package nl.bryanderidder.ornaguide.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentItemListBinding
import org.koin.android.viewmodel.ext.android.getViewModel


class ItemListFragment : BindingFragment<FragmentItemListBinding>(R.layout.fragment_item_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFragment
            adapter = ItemListAdapter(getViewModel())
            vm = getViewModel()
        }.root
    }
}