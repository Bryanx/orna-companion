package nl.bryanderidder.ornaguide.item.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemFilterCategoryBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * A small list of possible categories to filter items on
 * @author Bryan de Ridder
 */
class ItemListFilterCategoryFragment : BindingFragment<FragmentDialogItemFilterCategoryBinding>(R.layout.fragment_dialog_item_filter_category) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFilterCategoryFragment
            vm = getSharedViewModel()
        }.root
    }
}