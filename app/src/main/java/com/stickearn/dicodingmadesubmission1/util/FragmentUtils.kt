package com.stickearn.dicodingmadesubmission1.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

/**
 * Created by devis on 2020-01-05
 */
 
fun FragmentActivity.replaceFragment(fragment: Fragment, idContainer: Int, tag: String?) {
    supportFragmentManager.beginTransaction()
        .replace(idContainer, fragment, tag)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()
}