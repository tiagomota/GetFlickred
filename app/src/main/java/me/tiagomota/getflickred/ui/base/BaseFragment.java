package me.tiagomota.getflickred.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.tiagomota.getflickred.ui.base.injection.components.FragmentComponent;
import me.tiagomota.getflickred.ui.base.injection.modules.FragmentModule;

public abstract class BaseFragment extends Fragment {

    private FragmentComponent mFragmentComponent;

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            // inject hte Object graph dependencies here.
            final BaseActivity activity = (BaseActivity) getActivity();
            mFragmentComponent = activity.getPersistentComponent().plus(new FragmentModule());
        } catch (final ClassCastException ex) {
            Log.e("BaseFragment", "BaseFragment needs to be instantiated in a BaseActivity.", ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(getFragmentLayout(), container, false);
        mapLayoutViews(root);
        return root;
    }

    protected FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    /**
     * Returns the chosen layout for this fragment.
     *
     * @return int
     */
    protected abstract @LayoutRes int getFragmentLayout();

    /**
     * Requires the activity to map the necessary layout views
     * to instance variables, for better organization.
     *
     * @param root View
     */
    protected abstract void mapLayoutViews(final View root);
}
