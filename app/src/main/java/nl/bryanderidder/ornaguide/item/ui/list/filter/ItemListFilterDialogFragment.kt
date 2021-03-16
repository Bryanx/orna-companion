package nl.bryanderidder.ornaguide.item.ui.list.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemFilterBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Filter items in a bottom sheet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class ItemListFilterDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogItemFilterBinding>(R.layout.fragment_dialog_item_filter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFilterDialogFragment
            vm = getSharedViewModel()
            dialog = this@ItemListFilterDialogFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            filterViewpager.adapter = ItemListFilterPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        }
        TabLayoutMediator(binding.filterTabLayout, binding.filterViewpager) { tab, position ->
            tab.text = ItemListFilterPagerAdapter.FILTER_TAB_LABELS[(position)]
        }.attach()
    }

}