package nl.bryanderidder.ornaguide.item.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemFilterStatBinding
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemFilterTypeBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * A small list of possible stats to filter items on
 * @author Bryan de Ridder
 */
class ItemListFilterStatFragment : BindingFragment<FragmentDialogItemFilterStatBinding>(R.layout.fragment_dialog_item_filter_stat) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFilterStatFragment
            vm = getSharedViewModel()
        }.root
    }
}