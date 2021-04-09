package nl.bryanderidder.ornaguide.achievement.ui.list.filter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogAchievementFilterBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Filter achievements in a bottom sheet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class AchievementListFilterDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogAchievementFilterBinding>(R.layout.fragment_dialog_achievement_filter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@AchievementListFilterDialogFragment
            vm = getSharedViewModel()
            dialog = this@AchievementListFilterDialogFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            filterViewpager.adapter = AchievementListFilterPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        }
        TabLayoutMediator(binding.filterTabLayout, binding.filterViewpager) { tab, position ->
            tab.text = AchievementListFilterPagerAdapter.FILTER_TAB_LABELS[(position)]
        }.attach()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.vm?.onDismissed()
    }

}