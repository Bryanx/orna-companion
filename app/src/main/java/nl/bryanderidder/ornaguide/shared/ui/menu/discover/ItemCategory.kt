package nl.bryanderidder.ornaguide.shared.ui.menu.discover

import nl.bryanderidder.ornaguide.R


data class ItemCategory(
    val drawable: Int,
    val title: String,
    val layout: Int = 0,
) {

    companion object {
        val CATEGORIES = listOf(
            ItemCategory(R.drawable.classes, "Classes", R.id.action_dicoverFragment_to_characterClassListFragment),
            ItemCategory(R.drawable.specializations, "Specializations", R.id.action_dicoverFragment_to_specializationListFragment),
            ItemCategory(R.drawable.achievements, "Achievements", R.id.action_dicoverFragment_to_achievementListFragment),
            ItemCategory(R.drawable.npcs, "NPCs", R.id.action_dicoverFragment_to_npcListFragment),
            ItemCategory(R.drawable.pets, "Pets", R.id.action_dicoverFragment_to_petListFragment),
            ItemCategory(R.drawable.skills, "Skills", R.id.action_dicoverFragment_to_skillListFragment),
            ItemCategory(R.drawable.monsters, "Monsters", R.id.action_dicoverFragment_to_monsterListFragment),
            ItemCategory(R.drawable.items, "Items", R.id.action_dicoverFragment_to_itemListFragment),
        )
    }
}