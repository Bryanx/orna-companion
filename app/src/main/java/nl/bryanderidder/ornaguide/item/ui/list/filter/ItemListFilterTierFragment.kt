package nl.bryanderidder.ornaguide.item.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemFilterTierBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * A small list of possible tiers to filter items on
 * @author Bryan de Ridder
 */
class ItemListFilterTierFragment : BindingFragment<FragmentDialogItemFilterTierBinding>(R.layout.fragment_dialog_item_filter_tier) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFilterTierFragment
            vm = getSharedViewModel()
        }.root
    }
}