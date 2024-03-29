package nl.bryanderidder.ornaguide.skill.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nl.bryanderidder.ornaguide.shared.util.ImageUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.ui.detail.BuffedByAdapter
import nl.bryanderidder.ornaguide.skill.ui.detail.LearnedByAdapter
import nl.bryanderidder.ornaguide.skill.ui.detail.MonstersUseAdapter
import nl.bryanderidder.ornaguide.skill.ui.detail.PetsUseAdapter

object SkillViewBinding {

    @JvmStatic
    @BindingAdapter("skillSrc")
    fun bindSkillSrc(view: ImageView, type: String?) {
        Glide.with(view.context)
            .load(ImageUtil.getSkillTypeImage(view.context, type))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("learnedByAdapter", "learnedByList", requireAll = true)
    fun bindAdapterLearnedByList(view: RecyclerView, adapter: LearnedByAdapter, items: List<Skill.LearnedBy>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("buffedByAdapter", "buffedByList", requireAll = true)
    fun bindBuffedByList(view: RecyclerView, adapter: BuffedByAdapter, items: List<Skill.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("monstersUseAdapter", "monstersUseList", requireAll = true)
    fun bindMonstersUseList(view: RecyclerView, adapter: MonstersUseAdapter, items: List<Skill.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("petsUseAdapter", "petsUseList", requireAll = true)
    fun bindPetsUseList(view: RecyclerView, adapter: PetsUseAdapter, items: List<Skill.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }
}