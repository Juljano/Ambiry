package de.jukolai.ambiry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;


public class OverviewFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewpagerOverviewAdapter viewpageAdapter;
    ImageButton imageButtonBackButton, imageButtonNotification;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewpager);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        viewpageAdapter = new ViewpagerOverviewAdapter(fragmentManager, getLifecycle());
        viewPager.setAdapter(viewpageAdapter);

        // The Tabs get a Namen
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tablayoutEpisode));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tablayoutAboutThePodcast));
        tabLayout.addTab(tabLayout.newTab().setText((R.string.tablayoutsimilarPodcats)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        return view;
    }
}