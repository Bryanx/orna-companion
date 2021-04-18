package nl.bryanderidder.ornaguide.pet.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentPetListBinding
import nl.bryanderidder.ornaguide.shared.util.navigateSafely
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class PetListFragment : BindingFragment<FragmentPetListBinding>(R.layout.fragment_pet_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@PetListFragment
            activity = requireActivity()
            adapter = PetListAdapter(get())
            sharedPrefsUtil = get()
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                navigateSafely(R.id.action_petListFragment_to_petListFilterDialogFragment)
            }
        }.root
    }
}