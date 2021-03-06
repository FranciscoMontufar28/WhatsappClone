package com.francisco.whatsapptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.adapter.ViewPagerChatAdapter
import com.francisco.whatsapptest.databinding.FragmentMainChatBinding
import com.francisco.whatsapptest.di.MainChatComponent
import com.francisco.whatsapptest.di.MainChatModule
import com.francisco.whatsapptest.presentation.MainChatViewModel
import com.francisco.whatsapptest.util.getViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mancj.materialsearchbar.MaterialSearchBar

class MainChatFragment : Fragment(), MaterialSearchBar.OnSearchActionListener {

    private lateinit var mainChatComponent: MainChatComponent
    private var _binding: FragmentMainChatBinding? = null
    private val binding get() = _binding!!
    private val viewPagerAdapter: ViewPagerChatAdapter by lazy { ViewPagerChatAdapter(this) }
    private val viewPager: ViewPager2 by lazy { binding.mainChatViewPager }
    private val tabLayout: TabLayout by lazy { binding.mainChatTabLayout }
    private val mainChatViewModel: MainChatViewModel by lazy {
        getViewModel { mainChatComponent.mainChatViewModel }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainChatComponent =
            (requireActivity().application as WhatsApp).getAppComponent().inject(
                MainChatModule()
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchBar = binding.searchBarChat
        searchBar.setOnSearchActionListener(this)
        searchBar.inflateMenu(R.menu.chat_menu)
        searchBar.menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.newGroup -> {
                    createGroup()
                }
                R.id.newBroadcast -> {
                    createBroadcast()
                }
                R.id.settings -> {
                    openSetting()
                }
                R.id.signOut -> {
                    signOut()
                }
                else -> {
                    //noOp
                }
            }
            return@setOnMenuItemClickListener true
        }
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        viewPagerAdapter.setFragments(
            arrayListOf(
                ChatListFragment(),
                StatusListFragment(),
                CallListFragment()
            )
        )
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Chats"
                1 -> "Status"
                2 -> "Calls"
                else -> ""
            }
        }.attach()
    }

    private fun signOut() {
        mainChatViewModel.signOut()
        mainChatViewModel.updateUserLoginAuth(requireContext(), false)
        toGoOnBoarding()
    }

    private fun toGoOnBoarding() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_mainChatFragment_to_onBoardingFragment)
    }

    private fun openSetting() {

    }

    private fun createGroup() {

    }

    private fun createBroadcast() {

    }

    override fun onButtonClicked(buttonCode: Int) {

    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onSearchConfirmed(text: CharSequence?) {

    }
}