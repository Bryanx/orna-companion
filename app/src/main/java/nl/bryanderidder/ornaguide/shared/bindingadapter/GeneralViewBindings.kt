package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.attrColor
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup


object GeneralViewBindings {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        if (!text.isNullOrEmpty())
            Toast.makeText(view.context, text, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("glideSrc")
    fun bindGlideSrc(view: AppCompatImageView, url: String?) {
        if (url.isNullOrEmpty())
            return
        val circularProgressDrawable = CircularProgressDrawable(view.context)
            .apply { strokeWidth = 5f }
            .apply { centerRadius = 5f }
            .also { it.start() }
        val requestOptions = RequestOptions()
            .placeholder(circularProgressDrawable)
            .error(R.drawable.ic_baseline_clear_24)
            .skipMemoryCache(true)
            .fitCenter()
        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("glideSrcNoPlaceholder")
    fun bindGlideSrcNoPlaceholder(view: AppCompatImageView, url: String?) {
        if (url.isNullOrEmpty())
            return
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("textChangedListener")
    fun onTextChanged(et: TextInputEditText, callback: StringConsumer) {
        et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                callback.accept(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    @JvmStatic
    @BindingAdapter("clearOnClickRightDrawable")
    fun onClickRightDrawable(et: TextInputEditText, accept: Boolean) {
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3
        et.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= et.right - et.compoundDrawables[DRAWABLE_RIGHT].bounds.width() * 2) {
                    et.text?.clear()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    @JvmStatic
    @BindingAdapter("closeOnDone")
    fun bindCloseOnDone(et: TextInputEditText, accept: Boolean) {
        et.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm: InputMethodManager =
                    et.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(et.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
    }

    @JvmStatic
    @BindingAdapter("onChanged")
    fun bindButtonGroupChanged(view: ThemedToggleButtonGroup, callback: StringListConsumer) {
        view.setOnSelectListener {
            it.btnHeight = FrameLayout.LayoutParams.WRAP_CONTENT
            callback.accept(view.selectedButtons.map(ThemedButton::text).toList())
        }
    }

    @JvmStatic
    @BindingAdapter("listenOnClick")
    fun bindListenOnClick(view: View, callback: () -> Unit) {
        view.setOnClickListener {
            callback.invoke()
        }
    }

    @JvmStatic
    @BindingAdapter("onClickWithSelf")
    fun bindOnClick(view: View, callback: ViewConsumer) {
        view.setOnClickListener {
            callback.accept(it)
        }
    }
}

interface StringConsumer {
    fun accept(value: String)
}
interface ViewConsumer {
    fun accept(value: View)
}
interface StringListConsumer {
    fun accept(value: List<String>)
}