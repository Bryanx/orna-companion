package nl.bryanderidder.ornaguide.item.ui

import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDroppedByAdapter
import nl.bryanderidder.ornaguide.item.ui.detail.ItemEquippedByAdapter
import nl.bryanderidder.ornaguide.item.ui.detail.ItemMaterialsAdapter
import nl.bryanderidder.ornaguide.item.ui.detail.ItemQuestsAdapter
import nl.bryanderidder.ornaguide.shared.util.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup

object ItemViewBinding {

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
    fun bindItemQualityColor(view: TextView, quality: String) {
        if (quality.isEmpty()) return
        val qualityPercent = (quality.toFloat() * 100).toInt()
        when {
            qualityPercent < 100 -> view.setTextColor(view.context.color(R.color.itemCategoryBroken))
            qualityPercent < 110 -> view.setTextColor(view.context.color(R.color.itemCategoryCommon))
            qualityPercent < 121 -> view.setTextColor(view.context.color(R.color.itemCategorySuperior))
            qualityPercent < 140 -> view.setTextColor(view.context.color(R.color.itemCategoryFamed))
            qualityPercent < 171 -> view.setTextColor(view.context.color(R.color.itemCategoryLegendary))
            else -> view.setTextColor(view.context.color(R.color.itemCategoryOrnate))
        }
    }

    @JvmStatic
    @BindingAdapter("itemQualityText")
    fun bindItemQualityText(view: TextView, quality: String) {
        if (quality.isEmpty()) return
        val qualityPercent = (quality.toFloat() * 100).toInt()
        when {
            qualityPercent < 100 -> view.text = "Broken/Poor"
            qualityPercent < 110 -> view.text = "Common"
            qualityPercent < 121 -> view.text = "Superior"
            qualityPercent < 140 -> view.text = "Famed"
            qualityPercent < 171 -> view.text = "Legendary"
            else -> view.text = "Ornate"
        }
    }
}