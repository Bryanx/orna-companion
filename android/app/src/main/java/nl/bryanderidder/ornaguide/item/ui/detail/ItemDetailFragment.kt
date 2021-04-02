package nl.bryanderidder.ornaguide.item.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentItemDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel

/**
 * Tab where users can see item details
 * @author Bryan de Ridder
 */
class ItemDetailFragment : BindingFragment<FragmentItemDetailBinding>(R.layout.fragment_item_detail) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemDetailFragment
            itemEquippedByAdapter = ItemEquippedByAdapter(get())
            itemDroppedByAdapter = ItemDroppedByAdapter(get())
            itemMaterialsAdapter = ItemMaterialsAdapter(get())
            itemQuestsAdapter = ItemQuestsAdapter(get())
            vm = getSharedViewModel()
        }.root
    }
}