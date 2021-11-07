package nl.bryanderidder.ornaguide.save.ui.filter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSaveFilterBinding
import nl.bryanderidder.ornaguide.save.ui.filter.SaveListFilterPagerAdapter
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Filter saves in a bottom sheet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class SaveListFilterDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogSaveFilterBinding>(R.layout.fragment_dialog_save_filter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SaveListFilterDialogFragment
            vm = getSharedViewModel()
            dialog = this@SaveListFilterDialogFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            filterViewpager.adapter = SaveListFilterPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        }
        TabLayoutMediator(binding.filterTabLayout, binding.filterViewpager) { tab, position ->
            tab.text = SaveListFilterPagerAdapter.FILTER_TAB_LABELS[(position)]
        }.attach()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.vm?.onDismissed()
    }

}