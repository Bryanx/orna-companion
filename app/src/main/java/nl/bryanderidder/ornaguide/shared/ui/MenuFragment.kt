package nl.bryanderidder.ornaguide.shared.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.MenuBottomsheetBinding


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
        val pager = (activity as MainActivity).findViewById<ViewPager2>(R.id.main_viewpager)
        binding.bottomMenu.setSelectedItem(pager.currentItem)
        binding.bottomMenu.setOnItemSelectedListener { item, index ->
            pager.currentItem = index
            dismiss()
        }
        return binding.root
    }

}