package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.google.android.material.transition.MaterialFadeThrough
import com.skydoves.bindables.BindingFragment


/**
 * Base fragment for menu items
 * @author Bryan de Ridder
 */
abstract class BaseMenuFragment<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BindingFragment<T>(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }
}