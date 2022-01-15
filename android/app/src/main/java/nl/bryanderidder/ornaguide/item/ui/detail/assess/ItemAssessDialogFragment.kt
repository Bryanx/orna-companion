package nl.bryanderidder.ornaguide.item.ui.detail.assess

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.skydoves.bindables.BindingBottomSheetDialogFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogItemAssessBinding
import nl.bryanderidder.ornaguide.shared.util.setOnShowBottomSheet
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
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemAssessDialogFragment
            vm = getSharedViewModel<ItemAssessViewModel>().apply { loadItem() }
            context = requireContext()
            dialog = this@ItemAssessDialogFragment
        }.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowBottomSheet { behavior ->
            behavior.peekHeight = (Resources.getSystem().displayMetrics.heightPixels) / 2
        }
        return bottomSheetDialog
    }
}