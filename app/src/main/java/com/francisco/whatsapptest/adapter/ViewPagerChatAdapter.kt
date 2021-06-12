package com.francisco.whatsapptest.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerChatAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var listFragment: List<Fragment> = arrayListOf()

    fun setFragments(listFragment: List<Fragment>) {
        this.listFragment = listFragment
    }

    override fun getItemCount(): Int {
        return listFragment.count()
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}