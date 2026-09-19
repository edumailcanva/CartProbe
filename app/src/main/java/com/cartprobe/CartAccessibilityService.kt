package com.cartprobe

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class CartAccessibilityService : AccessibilityService() {

    companion object {
        var instance: CartAccessibilityService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // We only read the screen when the user presses READ CART.
    }

    override fun onInterrupt() {
    }

    fun readCurrentScreen(): String {
        val root = rootInActiveWindow ?: return "Could not read the current screen."

        val result = StringBuilder()
        collectText(root, result)

        return if (result.isEmpty()) {
            "No readable text found."
        } else {
            result.toString()
        }
    }

    private fun collectText(
        node: AccessibilityNodeInfo,
        result: StringBuilder
    ) {
        node.text?.let {
            if (it.isNotBlank()) {
                result.append(it).append("\n")
            }
        }

        node.contentDescription?.let {
            if (it.isNotBlank()) {
                result.append(it).append("\n")
            }
        }

        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { child ->
                collectText(child, result)
                child.recycle()
            }
        }
    }
}
