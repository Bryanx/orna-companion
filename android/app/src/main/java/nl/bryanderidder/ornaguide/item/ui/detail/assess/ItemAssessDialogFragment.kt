package nl.bryanderidder.ornaguide.item.ui.detail.assess

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemAssessBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel


/**
 * Bottom sheet for item assess details
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class ItemAssessDialogFragment : BindingBottomSheetDialogFragment<FragmentDialogItemAssessBinding>(R.layout.fragment_dialog_item_assess) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemAssessDialogFragment
            vm = getSharedViewModel<ItemAssessViewModel>().apply { loadItem() }
            context = requireContext()
            dialog = this@ItemAssessDialogFragment
        }.root
    }
}