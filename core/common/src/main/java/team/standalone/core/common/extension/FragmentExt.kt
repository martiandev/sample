package team.standalone.core.common.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectLatestLifecycleFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> Fragment.collectLifecycleFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

fun <T : Any> Fragment.sendDestinationResult(key: String, value: T?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T : Any> Fragment.observeFragmentResult(
    key: String,
    onlyOnce: Boolean = true,
    block: (T?) -> Unit,
) {
    findNavController().currentBackStackEntry?.savedStateHandle?.apply {
        getLiveData<T>(key).observe(viewLifecycleOwner) {
            block(it)
            if (onlyOnce) remove<T>(key)
        }
    }
}

fun <T : Any> Fragment.observeDialogResult(
    key: String,
    @IdRes destinationId: Int,
    onlyOnce: Boolean = true,
    block: (T?) -> Unit,
) {
    val navController = findNavController()
    val navBackStackEntry = navController.getBackStackEntry(destinationId)
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains(key)) {
            val result = navBackStackEntry.savedStateHandle.get<T>(key)
            block(result)
            if (onlyOnce) navBackStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    navBackStackEntry.lifecycle.addObserver(observer)

    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            navBackStackEntry.lifecycle.removeObserver(observer)
        }
    })
}