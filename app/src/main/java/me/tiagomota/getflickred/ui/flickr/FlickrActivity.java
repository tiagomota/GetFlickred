package me.tiagomota.getflickred.ui.flickr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.ui.base.BaseActivity;
import me.tiagomota.getflickred.ui.flickr.list.FlickrPhotosListFragment;

public class FlickrActivity extends BaseActivity {

    private static final String KEY_USER = "key_user";

    private User mUser;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_flickr;
    }

    @Override
    protected void mapLayoutViews() {
        // no-op
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get user object
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_USER)) {
            mUser = savedInstanceState.getParcelable(KEY_USER);
        } else if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(KEY_USER)) {
            mUser = getIntent().getExtras().getParcelable(KEY_USER);
        }

        // Compose the UI according to the selected layout, Handset or Tablet.
        if (findViewById(R.id.fragments_container) != null) {
            // handset device
            final FlickrPhotosListFragment fragment = FlickrPhotosListFragment.newInstance(mUser.getId());
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragments_container, fragment, FlickrPhotosListFragment.TAG);
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();

        } else {
            // tablet device
            //TODO
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_USER, mUser);
    }

    /**
     * Creates a new instance of the FlickrActivity and starts it with the necessary extra information.
     *
     * @param caller Activity
     * @param user   User
     */
    public static void newInstance(final Activity caller, final User user) {
        final Bundle extras = new Bundle();
        extras.putParcelable(KEY_USER, user);

        final Intent intent = new Intent(caller, FlickrActivity.class);
        intent.putExtras(extras);

        ActivityCompat.startActivity(caller, intent, null);
    }
}
