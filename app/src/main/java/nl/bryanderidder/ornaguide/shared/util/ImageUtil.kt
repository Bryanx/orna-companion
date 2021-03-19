package nl.bryanderidder.ornaguide.shared.util

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView

object ImageUtil {
    fun getImage(context: Context, imageName: String?): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }

    fun getSkillTypeImage(context: Context, type: String?): Int = getImage(context, when {
            type == null -> "attack"
            type.contains("Attack") -> "attack"
            type.contains("Passive") -> "passive"
            else -> "magic"
        }
    )

    fun AppCompatImageView.setImage(resId: Int) =
        setImageDrawable(AppCompatResources.getDrawable(context, resId))
}