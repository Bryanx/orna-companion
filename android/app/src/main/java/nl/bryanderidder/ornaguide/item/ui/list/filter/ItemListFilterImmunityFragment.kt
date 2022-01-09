package nl.bryanderidder.ornaguide.item.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemFilterImmunityBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * A small list of possible types to filter items on
 * @author Bryan de Ridder
 */
class ItemListFilterImmunityFragment : BindingFragment<FragmentDialogItemFilterImmunityBinding>(R.layout.fragment_dialog_item_filter_immunity) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFilterImmunityFragment
            vm = getSharedViewModel()
        }.root
    }
}