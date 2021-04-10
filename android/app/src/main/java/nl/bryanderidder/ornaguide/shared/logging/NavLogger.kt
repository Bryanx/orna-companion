package nl.bryanderidder.ornaguide.shared.logging

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import org.json.JSONObject
import timber.log.Timber


/**
 * Description
 * @author Bryan de Ridder
 */
class NavLogger(private val context: Context) :
    NavController.OnDestinationChangedListener {

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val res = context.resources
        Timber.i(
            JSONObject()
                .put("navigateTo", res.getResourceEntryName(destination.id))
                .toString()
        )
    }
}