package nl.bryanderidder.ornaguide.characterclass.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentCharacterClassListBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.ext.android.get


class CharacterClassListFragment : BindingFragment<FragmentCharacterClassListBinding>(R.layout.fragment_character_class_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val sharedPrefsUtil: SharedPrefsUtil = get()
        return binding {
            lifecycleOwner = this@CharacterClassListFragment
            adapter = CharacterClassListAdapter(sharedPrefsUtil)
            vm = getViewModel()
        }.root
    }
}