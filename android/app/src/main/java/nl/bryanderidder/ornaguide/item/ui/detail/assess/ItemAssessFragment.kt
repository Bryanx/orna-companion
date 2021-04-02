package nl.bryanderidder.ornaguide.item.ui.detail.assess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentItemAssessBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel
import org.koin.android.viewmodel.ext.android.getViewModel


/**
 * Tab where users can assess an item
 * @author Bryan de Ridder
 */
class ItemAssessFragment : BindingFragment<FragmentItemAssessBinding>(R.layout.fragment_item_assess) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemAssessFragment
            vm = getSharedViewModel()
            assessVM = getViewModel()
        }.root
    }
}