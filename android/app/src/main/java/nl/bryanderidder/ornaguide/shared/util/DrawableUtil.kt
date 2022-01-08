package nl.bryanderidder.ornaguide.shared.util

import com.google.android.material.badge.BadgeDrawable

var BadgeDrawable?.positiveNumber: Int
    get() = this?.number ?: 0
    set(value: Int) {
        this?.isVisible = value > 0
        this?.number = value
    }