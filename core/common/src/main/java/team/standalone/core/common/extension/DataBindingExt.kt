package team.standalone.core.common.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * * This is an extension function that set lifecycleOwner to binding.
 */
inline fun <B : ViewDataBinding> withDataBinding(
    fragment: Fragment,
    binding: B,
    body: B.() -> Unit = {},
): B {
    binding.lifecycleOwner = fragment.viewLifecycleOwner
    binding.body()
    return binding
}

/**
 * This is an extension function that calls to [DataBindingUtil.setContentView].
 * Gets the team.standalone.core.data.data binding for layout
 */
fun <B : ViewDataBinding> FragmentActivity.withDataBinding(
    @LayoutRes layoutResId: Int,
    body: B.() -> Unit = {},
): B {
    val binding: B = DataBindingUtil.setContentView(this, layoutResId)
    binding.lifecycleOwner = this
    binding.body()
    return binding
}

/**
 * This is an extension function that calls to [DataBindingUtil.inflate].
 * Gets the team.standalone.core.data.data binding for layout
 */
@Deprecated("Use withDataBinding")
internal fun <B : ViewDataBinding> Fragment.withDataBinding(
    inflater: LayoutInflater,
    @LayoutRes layoutResId: Int,
    parent: ViewGroup?,
    body: B.() -> Unit = {},
): B {
    val binding: B = DataBindingUtil.inflate(inflater, layoutResId, parent, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.body()
    return binding
}

/**
 * This is an extension function that calls to [DataBindingUtil.inflate].
 * Gets the team.standalone.core.data.data binding for layout
 */
fun <B : ViewDataBinding> ViewGroup.withDataBinding(@LayoutRes layoutResId: Int): B {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, this, false)
}
