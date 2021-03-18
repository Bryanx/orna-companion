package nl.bryanderidder.ornaguide.characterclass.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogCharacterClassFilterCostTypeBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class CharacterClassListFilterCostTypeFragment : BindingFragment<FragmentDialogCharacterClassFilterCostTypeBinding>(R.layout.fragment_dialog_character_class_filter_cost_type) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@CharacterClassListFilterCostTypeFragment
            vm = getSharedViewModel()
        }.root
    }
}