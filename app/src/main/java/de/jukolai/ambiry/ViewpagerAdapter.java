package de.jukolai.ambiry;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewpagerAdapter extends FragmentStateAdapter {


    public ViewpagerAdapter(@NonNull FragmentManager fragmentActivity, Lifecycle lifecycle) {
        super(fragmentActivity, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            return new SubscriptionFragment();
        }
        return new PlaylistFragment();

    }

    @Override
    public int getItemCount() {
        return 2; // 2 Tabs are available
    }

}
