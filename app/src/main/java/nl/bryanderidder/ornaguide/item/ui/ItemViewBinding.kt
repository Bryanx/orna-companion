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
import nl.bryanderidder.ornaguide.item.ui.list.ItemListAdapter
import nl.bryanderidder.ornaguide.shared.util.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup

object ItemViewBinding {

    @JvmStatic
    @BindingAdapter("itemAdapter", "itemAdapterList")
    fun bindAdapterItemList(
        view: RecyclerView,
        itemListAdapter: ItemListAdapter,
        items: List<Item>?,
    ) {
        if (view.adapter == null)
            view.adapter = itemListAdapter
        (view.adapter as ItemListAdapter).submitList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("toggleTierButtons", "toggleTierInitiallySelectedButtons")
    fun bindToggleTierButtons(view: ThemedToggleButtonGroup, items: List<Int>?, selectedItems: List<Int>?) {
        val presentButtons = view.buttons.map(ThemedButton::text).toList()
        items?.filter { !presentButtons.contains(it.toString()) }
            ?.forEach { item ->
                val btn = ThemedButton(view.context)
                btn.text = item.toString()
                btn.bgColor = view.context.color(R.color.cardColor)
                btn.selectedBgColor = view.context.attrColor(R.attr.colorPrimary)
                btn.applyToTexts {
                    it.layoutGravity = Gravity.CENTER
                    it.setViewMargin(left = 10.dp)
                }
                ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_star_24)?.let {
                    btn.applyToIcons { icon ->
                        icon.setImageDrawable(it)
                        icon.layoutGravity = Gravity.CENTER
                        icon.setViewMargin(right = 10.dp)
                    }
                }
                view.addView(btn,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                btn.applyToCards {
                    it.setViewMargin(vertical = 6.dp)
                    it.setViewPadding(horizontal = 12.dp.toFloat(), vertical = 4.dp.toFloat())
                }
                if (selectedItems?.contains(item) == true)
                    view.selectButton(btn)
            }
    }

    @JvmStatic
    @BindingAdapter("toggleButtons", "toggleInitiallySelectedButtons")
    fun bindToggleButtons(view: ThemedToggleButtonGroup, items: List<String>?, selectedItems: List<String>?) {
        val presentButtons = view.buttons.map(ThemedButton::text).toList()
        items?.filter { !presentButtons.contains(it) }
            ?.forEach { item ->
                val btn = ThemedButton(view.context)
                btn.text = item
                btn.bgColor = view.context.color(R.color.cardColor)
                btn.selectedBgColor = view.context.attrColor(R.attr.colorPrimary)
                view.addView(btn,
                    RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                btn.applyToCards {
                    it.setViewMargin(vertical = 6.dp)
                    it.setViewPadding(horizontal = 8.dp.toFloat(), vertical = 0.dp.toFloat())
                }
                if (selectedItems?.contains(item) == true)
                    view.selectButton(btn)
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
}