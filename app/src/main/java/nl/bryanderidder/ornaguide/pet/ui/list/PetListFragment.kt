package nl.bryanderidder.ornaguide.pet.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentPetListBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

class PetListFragment : BindingFragment<FragmentPetListBinding>(R.layout.fragment_pet_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@PetListFragment
            adapter = PetListAdapter(get())
            vm = getViewModel()
        }.root
    }
}