package com.goforer.base.designsystem.component.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * Returns a dummy MutableState that does not cause render when setting it
 */
@Composable
fun <Int> rememberRef(): MutableState<Int?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember {
        object: MutableState<Int?> {
            override var value: Int? = null

            override fun component1(): Int? = value

            override fun component2(): (Int?) -> Unit = { value = it }
        }
    }
}

@Composable
fun <Int> rememberPrevious(
    current: Int,
    shouldUpdate: (prev: Int?, curr: Int) -> Boolean = { a: Int?, b: Int -> a != b },
): Int? {
    val ref = rememberRef<Int>()

    // launched after render, so the current render will have the old value anyway
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun LazyListState.rememberCurrentScrollOffset(): State<Int> {
    val position: State<Int> = remember { derivedStateOf { this.firstVisibleItemIndex } }
    val firstVisibleItemScrollOffset : State<Int> = remember { derivedStateOf { this.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastVisibleItemScrollOffset  = rememberPrevious(firstVisibleItemScrollOffset .value)
    val currentScrollOffset: MutableState<Int> = remember { mutableIntStateOf(0) }

    LaunchedEffect(position.value, firstVisibleItemScrollOffset .value) {
        when {
            lastPosition == null || position.value == 0 ->  currentScrollOffset.value = firstVisibleItemScrollOffset .value
            lastPosition == position.value -> currentScrollOffset.value += (firstVisibleItemScrollOffset .value - (lastVisibleItemScrollOffset ?: 0))
            lastPosition > position.value -> currentScrollOffset.value -= (lastVisibleItemScrollOffset ?: 0)
            else -> currentScrollOffset.value += firstVisibleItemScrollOffset .value
        }
    }

    return currentScrollOffset
}