package de.jukolai.ambiry;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewpagerOverviewAdapter extends FragmentStateAdapter {

    // The Adapter is for the OverviewFragment
    public ViewpagerOverviewAdapter(@NonNull FragmentManager fragmentActivity, Lifecycle lifecycle) {
        super(fragmentActivity, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {

            return new DescriptionFragment();

        } else if (position == 2) {

            return new RecommandationFragment();

        }
        return new EpisodeFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
