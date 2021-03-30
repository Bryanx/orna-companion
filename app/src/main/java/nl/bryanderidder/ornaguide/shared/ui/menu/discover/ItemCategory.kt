package nl.bryanderidder.ornaguide.shared.ui.menu.discover

import nl.bryanderidder.ornaguide.R


data class ItemCategory(
    val drawable: Int,
    val title: String,
    val layout: Int = 0,
) {

    companion object {
        val CATEGORIES = listOf(
            ItemCategory(R.drawable.classes, "Classes", R.id.characterClassListFragment),
            ItemCategory(R.drawable.specializations, "Specializations", R.id.specializationListFragment),
            ItemCategory(R.drawable.achievements, "Achievements", R.id.achievementListFragment),
            ItemCategory(R.drawable.npcs, "NPCs", R.id.npcListFragment),
            ItemCategory(R.drawable.pets, "Pets", R.id.petListFragment),
            ItemCategory(R.drawable.skills, "Skills", R.id.skillListFragment),
            ItemCategory(R.drawable.monsters, "Monsters", R.id.monsterListFragment),
            ItemCategory(R.drawable.items, "Items", R.id.itemListFragment),
        )
    }
}