/*
 * Copyright (c) 2021 Sebastian Erives
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.serivesmejia.eocvsim.util.event

/**
 * Functional interface to be used as an event listener
 */
fun interface EventListener {
    /**
     * Function to be called when the event is emitted
     * @param remover the remover object to remove the listener
     */
    fun run(remover: EventListenerRemover)
}

/**
 * Class to remove an event listener
 * @param handler the event handler
 * @param listener the event listener
 * @param isOnceListener whether the listener is a once listener
 */
class EventListenerRemover(
    val handler: EventHandler,
    val listener: EventListener,
    val isOnceListener: Boolean
) {

    /**
     * Removes the listener from the event handler
     */
    fun removeThis() {
        if(isOnceListener)
            handler.removeOnceListener(listener)
        else
            handler.removePersistentListener(listener)
    }

}