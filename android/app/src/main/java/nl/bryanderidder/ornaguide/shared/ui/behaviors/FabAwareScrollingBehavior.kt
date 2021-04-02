package nl.bryanderidder.ornaguide.shared.ui.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FabAwareScrollingBehavior(context: Context?, attrs: AttributeSet?) :
    ScrollingViewBehavior(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return super.layoutDependsOn(parent, child, dependency) || dependency is FloatingActionButton
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type))
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray,
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
        when {
            dyConsumed > 0 && child is FloatingActionButton && child.isClickable ->
                child.animate()
                    .translationY(child.height.toFloat() + child.marginBottom)
                    .duration(100L)
                    .withEndAction { child.isClickable = true }
                    .withStartAction { child.isClickable = false }
            dyConsumed < 0 && child is FloatingActionButton && child.isClickable ->
                child.animate()
                    .translationY(0f)
                    .duration(100L)
                    .withEndAction { child.isClickable = true }
                    .withStartAction { child.isClickable = false }
        }
    }
}

private fun ViewPropertyAnimator.duration(fl: Long): ViewPropertyAnimator {
    duration = fl
    return this
}
