package me.tiagomota.getflickred.ui.flickr;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.ui.base.BaseActivity;
import me.tiagomota.getflickred.ui.flickr.list.FlickrPhotosListFragment;
import me.tiagomota.getflickred.utils.NetworkUtils;
import me.tiagomota.getflickred.utils.SnackBarFactory;
import me.tiagomota.getflickred.utils.ViewUtils;

public class FlickrActivity extends BaseActivity implements FlickrView {

    @Inject
    FlickrPresenter mPresenter;

    // General
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    // Toolbar
    private TextInputLayout mUsernameInputLayout;
    private EditText mUsernameEditText;
    private TextView mProgressIndicator;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_flickr;
    }

    @Override
    protected void mapLayoutViews() {
        // General
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        // Toolbar
        mUsernameInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_username);
        mUsernameEditText = (EditText) findViewById(R.id.edit_text_username);
        mProgressIndicator = (TextView) findViewById(R.id.progress_indicator);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);

        configureToolbarUsernameField();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onUserFound(final String userId, final String username, final String realName) {
        // add the list fragment to respective container
        final FlickrPhotosListFragment fragment = FlickrPhotosListFragment.newInstance(userId);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(
                isTablet() ? R.id.list_fragment_container : R.id.fragments_container,
                fragment,
                FlickrPhotosListFragment.TAG
        );
        ft.commit();
        getSupportFragmentManager().executePendingTransactions();

        // show progress indicator
        mProgressIndicator.setText(getString(R.string.flickr_loading_user_public_photos));
        mProgressIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUserNotFound(final String message) {
        // hide progress indicator
        mProgressIndicator.setVisibility(View.GONE);

        // hide content area
        displayContentArea(false);

        SnackBarFactory.build(
                mCoordinatorLayout,
                message,
                getString(R.string.action_retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchUsername();
                    }
                }
        ).show();
    }

    /**
     * Handles situation when the user is loaded but, there is no photos on his account.
     */
    public void onUserWithoutPhotos() {
        // hide progress indicator
        mProgressIndicator.setVisibility(View.GONE);

        SnackBarFactory.build(
                mCoordinatorLayout,
                getString(R.string.error_user_without_photos),
                getString(R.string.action_close)
        ).show();
    }

    /**
     * Handles the situation when the first batch of user public photos was loaded.
     */
    public void onUserFirstPhotosPageLoaded() {
        // hide progress indicator
        mProgressIndicator.setVisibility(View.GONE);

        // show content area.
        displayContentArea(true);
    }




    /**
     * Configures the functionality of the Toolbar Username field.
     */
    private void configureToolbarUsernameField() {
        mUsernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // hide keyboard
                    ViewUtils.hideKeyboard(FlickrActivity.this);

                    // search for inputted username
                    searchUsername();
                }
                return true;
            }
        });
    }

    /**
     * Requests the {@link FlickrPresenter} to search for the given username.
     */
    private void searchUsername() {
        if (NetworkUtils.isNetworkConnected(this)) {
            if (!TextUtils.isEmpty(mUsernameEditText.getText())) {
                // show progress indicator
                mProgressIndicator.setText(getString(R.string.flickr_loading_user));
                mProgressIndicator.setVisibility(View.VISIBLE);

                // clear any possible error
                mUsernameInputLayout.setError(null);
                // search username
                mPresenter.findUser(mUsernameEditText.getText().toString());
            } else {
                mUsernameInputLayout.setError(getString(R.string.error_field_empty));
            }
        } else {
            SnackBarFactory.build(
                    mCoordinatorLayout,
                    getString(R.string.error_no_network),
                    getString(R.string.action_retry),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            searchUsername();
                        }
                    }).show();
        }
    }


    /**
     * Utility method that verifies if the current device is an Hanset or Tablet.
     *
     * @return boolean
     */
    private boolean isTablet() {
        return findViewById(R.id.fragments_container) == null;
    }

    /**
     * Utility method used to changes the Collapsing toolbar specs to show or hide the content area.
     *
     * @param show boolean
     */
    private void displayContentArea(final boolean show) {
        // change appbar layout height
        final CoordinatorLayout.LayoutParams clp = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                show ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT
        );
        mAppBarLayout.setLayoutParams(clp);

        // change collapsing toolbar layout scroll flags
        final AppBarLayout.LayoutParams alp = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
        if (show) {
            alp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        } else {
            alp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        mCollapsingToolbarLayout.setLayoutParams(alp);
    }
}
