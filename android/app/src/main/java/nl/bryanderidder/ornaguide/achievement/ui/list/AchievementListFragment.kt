package nl.bryanderidder.ornaguide.achievement.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentAchievementListBinding
import nl.bryanderidder.ornaguide.shared.util.navigateSafely
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class AchievementListFragment : BindingFragment<FragmentAchievementListBinding>(R.layout.fragment_achievement_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@AchievementListFragment
            activity = requireActivity()
            adapter = AchievementListAdapter(get())
            sharedPrefsUtil = get()
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                navigateSafely(R.id.action_achievementListFragment_to_achievementListFilterDialogFragment)
            }
        }.root
    }
}