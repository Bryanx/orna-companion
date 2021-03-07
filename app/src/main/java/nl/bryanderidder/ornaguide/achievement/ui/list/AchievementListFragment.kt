package nl.bryanderidder.ornaguide.achievement.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentAchievementListBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

class AchievementListFragment : BindingFragment<FragmentAchievementListBinding>(R.layout.fragment_achievement_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@AchievementListFragment
            adapter = AchievementListAdapter(get())
            vm = getViewModel()
        }.root
    }
}