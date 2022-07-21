package com.stackexchangelite.app.utils

import android.util.Log
import androidx.fragment.app.FragmentActivity
import dagger.android.support.DaggerFragment

class AppUtils {
    companion object {
        val TAG = "AppUtils"

        fun replaceFragment(activity: FragmentActivity, fragment: DaggerFragment, animations: IntArray, fragmentView: Int) {
            Log.d(TAG, "replaceFragment: " + fragment.javaClass.name)
            val transaction = activity.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(animations[0], animations[1], animations[2], animations[3])
            transaction.replace(fragmentView, fragment)
            transaction.commit()
        }
    }
}