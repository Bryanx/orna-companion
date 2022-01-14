package nl.bryanderidder.ornaguide.item.ui.detail.assess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentItemAssessHistoryBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Tab where users can see the item's assess history
 * @author Bryan de Ridder
 */
class ItemAssessHistoryFragment : BindingFragment<FragmentItemAssessHistoryBinding>(R.layout.fragment_item_assess_history) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemAssessHistoryFragment
            assessVM = getSharedViewModel()
            adapter = ItemAssessHistoryAdapter(get(), this@ItemAssessHistoryFragment)
        }.root
    }
}