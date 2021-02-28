package nl.bryanderidder.ornaguide.shared.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.MenuBottomsheetBinding
import nl.bryanderidder.ornaguide.skill.ui.SkillListActivity


/**
 * Description
 * @author Bryan de Ridder
 */
class MenuFragment : BaseBottomSheet() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MenuBottomsheetBinding =
            DataBindingUtil.inflate(inflater, R.layout.menu_bottomsheet, container, false)
        binding.skills.setOnClickListener {
            startActivity(Intent(activity, SkillListActivity::class.java))
        }
        return binding.root
    }

}