package nl.bryanderidder.ornaguide.item.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentItemListBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


class ItemListFragment : BindingFragment<FragmentItemListBinding>(R.layout.fragment_item_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@ItemListFragment
            activity = requireActivity()
            adapter = ItemListAdapter(get())
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                findNavController().navigate(R.id.action_itemListFragment_to_itemListFilterDialogFragment)
            }
        }.root
    }
}