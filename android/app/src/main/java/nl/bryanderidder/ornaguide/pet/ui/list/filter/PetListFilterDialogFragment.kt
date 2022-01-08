package nl.bryanderidder.ornaguide.pet.ui.list.filter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogPetFilterBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.onPageSelected
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Filter pets in a bottom sheet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class PetListFilterDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogPetFilterBinding>(R.layout.fragment_dialog_pet_filter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@PetListFilterDialogFragment
            vm = getSharedViewModel()
            dialog = this@PetListFilterDialogFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            filterViewpager.adapter = PetListFilterPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            val sharedPrefs = get<SharedPrefsUtil>()
            filterViewpager.setCurrentItem(sharedPrefs.getPetFilterTab(), false)
            filterViewpager.onPageSelected(sharedPrefs::setPetFilterTab)
        }
        TabLayoutMediator(binding.filterTabLayout, binding.filterViewpager) { tab, position ->
            tab.text = PetListFilterPagerAdapter.FILTER_TAB_LABELS[(position)]
        }.attach()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.vm?.onDismissed()
    }

}