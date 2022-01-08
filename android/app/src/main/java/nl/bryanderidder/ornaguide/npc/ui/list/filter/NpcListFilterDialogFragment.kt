package nl.bryanderidder.ornaguide.npc.ui.list.filter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogNpcFilterBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.onPageSelected
import nl.bryanderidder.ornaguide.shared.util.positiveNumber
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Filter npcs in a bottom sheet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class NpcListFilterDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogNpcFilterBinding>(R.layout.fragment_dialog_npc_filter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@NpcListFilterDialogFragment
            vm = getSharedViewModel()
            dialog = this@NpcListFilterDialogFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            filterViewpager.adapter = NpcListFilterPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            val sharedPrefs = get<SharedPrefsUtil>()
            filterViewpager.setCurrentItem(sharedPrefs.getNpcFilterTab(), false)
            filterViewpager.onPageSelected(sharedPrefs::setNpcFilterTab)
            vm?.sessionNpcFilter?.observe(this@NpcListFilterDialogFragment) {
                it.getFilters().forEachIndexed { i, list ->
                    filterTabLayout.getTabAt(i)?.orCreateBadge?.positiveNumber = list.size
                }
            }
        }
        TabLayoutMediator(binding.filterTabLayout, binding.filterViewpager) { tab, position ->
            tab.text = NpcListFilterPagerAdapter.FILTER_TAB_LABELS[(position)]
            tab.orCreateBadge.backgroundColor = requireContext().color(R.color.ornaGreen)
        }.attach()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.vm?.onDismissed()
    }

}