package com.stickearn.dicodingmadesubmission1.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by devis on 2019-12-25
 */

class MainAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager!!) {

    private val tabNames: ArrayList<String> = arrayListOf()
    private val fragments: ArrayList<Fragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabNames[position]
    }

    fun add(fragment: Fragment, title: String) {
        tabNames.add(title)
        fragments.add(fragment)
    }

}