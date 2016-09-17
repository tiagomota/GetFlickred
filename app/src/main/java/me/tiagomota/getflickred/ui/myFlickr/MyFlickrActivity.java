package me.tiagomota.getflickred.ui.myFlickr;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.ui.base.BaseActivity;
import me.tiagomota.getflickred.ui.base.injection.components.ActivityComponent;
import me.tiagomota.getflickred.ui.myFlickr.list.MyFlickrPhotosListFragment;

public class MyFlickrActivity extends BaseActivity {

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_flickr;
    }

    @Override
    protected void mapLayoutViews() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.fragments_container) != null) {
            // handset device
            /*final MyFlickrPhotosListFragment fragment = MyFlickrPhotosListFragment.newInstance();
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragments_container, fragment, MyFlickrPhotosListFragment.TAG);
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();*/

        } else {
            // tablet device
            //TODO
        }
    }
}
