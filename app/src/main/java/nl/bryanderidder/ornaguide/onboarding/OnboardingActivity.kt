package nl.bryanderidder.ornaguide.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.attrColor
import nl.bryanderidder.ornaguide.shared.util.color

class OnboardingActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setImmersiveMode()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P)
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        val bgColor = color(R.color.backgroundColorDark)
        val bgColor2 = color(R.color.onboardingBackgroundColor)
        isSystemBackButtonLocked = true
        isSkipButtonEnabled = false

        setTransformer(AppIntroPageTransformerType.Parallax(
            titleParallaxFactor = 1.0,
            imageParallaxFactor = -2.0,
            descriptionParallaxFactor = 2.0
        ))
        setIndicatorColor(
            selectedIndicatorColor = attrColor(R.attr.colorPrimary),
            unselectedIndicatorColor = getColor(R.color.white50)
        )

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_intro))
        addSlide(AppIntroFragment.newInstance(
            title = "Search Orna database",
            titleColor = color(R.color.textColorHeader),
            description = "Search for items, monsters, skills,\nclasses and more...",
            descriptionColor = color(R.color.textColor),
            imageDrawable = R.drawable.onboard_search,
            backgroundColor = bgColor
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Filter results",
            titleColor = color(R.color.textColorHeader),
            description = "Filter results on tier, type, element\nand more...",
            descriptionColor = color(R.color.textColor),
            imageDrawable = R.drawable.onboard_filter,
            backgroundColor = bgColor2
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Assess item quality",
            titleColor = color(R.color.textColorHeader),
            description = "Check the quality of items and gain insight\ninto their stats when upgraded.",
            descriptionColor = color(R.color.textColor),
            imageDrawable = R.drawable.onboard_assess,
            backgroundColor = bgColor
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Save results",
            titleColor = color(R.color.textColorHeader),
            description = "Save items to keep a list\nof results at hand.",
            descriptionColor = color(R.color.textColor),
            imageDrawable = R.drawable.onboard_save,
            backgroundColor = bgColor2
        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}