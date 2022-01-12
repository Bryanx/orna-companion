package nl.bryanderidder.ornaguide.item.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.model.ItemAssess
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDroppedByAdapter
import nl.bryanderidder.ornaguide.item.ui.detail.ItemEquippedByAdapter
import nl.bryanderidder.ornaguide.item.ui.detail.ItemMaterialsAdapter
import nl.bryanderidder.ornaguide.item.ui.detail.ItemQuestsAdapter
import nl.bryanderidder.ornaguide.shared.ui.alerts.showAssessDialog
import nl.bryanderidder.ornaguide.shared.util.color

object ItemViewBinding {

    @JvmStatic
    @BindingAdapter("onClickShowAssessDialog")
    fun bindOnClickShowAssessDialog(view: View, itemAssess: ItemAssess) {
        view.setOnClickListener {
            view.context.showAssessDialog(itemAssess)
        }
    }

    @JvmStatic
    @BindingAdapter("arenaTextColor")
    fun bindArenaTextColor(view: TextView, text: String) {
        if (text == "Arena")
            view.setTextColor(view.context.color(R.color.arenaColor))
        else
            view.setTextColor(view.context.color(R.color.textColor))
    }

    @JvmStatic
    @BindingAdapter("itemEquippedByAdapter", "itemEquippedByList", requireAll = true)
    fun bindItemEquippedByAdapter(view: RecyclerView, adapter: ItemEquippedByAdapter, items: List<Item.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("itemDroppedByAdapter", "itemDroppedByList", requireAll = true)
    fun bindItemDroppedByAdapter(view: RecyclerView, adapter: ItemDroppedByAdapter, items: List<Item.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("itemMaterialsAdapter", "itemMaterialsList", requireAll = true)
    fun bindItemMaterialsAdapter(view: RecyclerView, adapter: ItemMaterialsAdapter, items: List<Item.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("itemQuestsAdapter", "itemQuestsList", requireAll = true)
    fun bindItemQuestsAdapter(view: RecyclerView, adapter: ItemQuestsAdapter, items: List<Item.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("itemQualityColor")
    fun bindItemQualityColor(view: TextView, itemAssess: ItemAssess) {
        view.setTextColor(itemAssess.getQualityColor(view.context))
    }
}