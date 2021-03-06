package nl.bryanderidder.ornaguide.shared.util

import android.content.Context

object ImageUtil {
    fun getImage(context: Context, imageName: String?): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}