package com.example.wallet.others

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPager (supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager) {
    private val moneyFragmentList: MutableList<Fragment> = ArrayList()
    private val moneyTitleList: MutableList<String> = ArrayList()

    override fun getCount(): Int {
        return moneyFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return moneyFragmentList[position]
    }
    fun addFragment(fragment: Fragment, title:String) {
        moneyFragmentList.add(fragment)
        moneyTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return moneyTitleList[position]
    }}