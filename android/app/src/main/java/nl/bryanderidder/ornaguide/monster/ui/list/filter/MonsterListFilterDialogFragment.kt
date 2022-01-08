package nl.bryanderidder.ornaguide.monster.ui.list.filter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogMonsterFilterBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.onPageSelected
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Filter monsters in a bottom sheet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class MonsterListFilterDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogMonsterFilterBinding>(R.layout.fragment_dialog_monster_filter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@MonsterListFilterDialogFragment
            vm = getSharedViewModel()
            dialog = this@MonsterListFilterDialogFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            filterViewpager.adapter = MonsterListFilterPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            val sharedPrefs = get<SharedPrefsUtil>()
            filterViewpager.setCurrentItem(sharedPrefs.getMonsterFilterTab(), false)
            filterViewpager.onPageSelected(sharedPrefs::setMonsterFilterTab)
        }
        TabLayoutMediator(binding.filterTabLayout, binding.filterViewpager) { tab, position ->
            tab.text = MonsterListFilterPagerAdapter.FILTER_TAB_LABELS[(position)]
        }.attach()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.vm?.onDismissed()
    }

}