package com.example.molysulfur.imageuser.idlingresource

import android.support.test.espresso.IdlingResource


/**
 * Contains a static reference IdlingResource, and should be available only in a mock build type.
 */
class EspressoIdlingResource {

    companion object {

        private val RESOURCE = "GLOBAL"

        private val mCountingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

        val idlingResource: IdlingResource
            get() = mCountingIdlingResource

        fun increment() {
            mCountingIdlingResource.increment()
        }

        fun decrement() {
            mCountingIdlingResource.decrement()
        }
    }
}