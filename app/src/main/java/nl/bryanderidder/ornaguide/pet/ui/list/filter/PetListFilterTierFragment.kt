package nl.bryanderidder.ornaguide.pet.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogPetFilterTierBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class PetListFilterTierFragment : BindingFragment<FragmentDialogPetFilterTierBinding>(R.layout.fragment_dialog_pet_filter_tier) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@PetListFilterTierFragment
            vm = getSharedViewModel()
        }.root
    }
}