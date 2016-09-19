package me.tiagomota.getflickred.ui.flickr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.ui.base.BaseActivity;
import me.tiagomota.getflickred.ui.flickr.detail.FlickrPhotoDetailFragment;
import me.tiagomota.getflickred.ui.flickr.list.FlickrPhotoListFragment;
import me.tiagomota.getflickred.utils.NetworkUtils;
import me.tiagomota.getflickred.utils.SnackBarFactory;
import me.tiagomota.getflickred.utils.ViewUtils;

public class FlickrActivity extends BaseActivity implements FlickrView {

    @Inject
    FlickrPresenter mPresenter;

    // General
    private CoordinatorLayout mCoordinatorLayout;

    // Toolbar
    private Toolbar mToolbar;
    private TextInputLayout mUsernameInputLayout;
    private EditText mUsernameEditText;
    private TextView mProgressIndicator;
    private Button mFindButton;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_flickr;
    }

    @Override
    protected void mapLayoutViews() {
        // General
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // Toolbar content
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mUsernameInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_username);
        mUsernameEditText = (EditText) findViewById(R.id.edit_text_username);
        mProgressIndicator = (TextView) findViewById(R.id.progress_indicator);
        mFindButton = (Button) findViewById(R.id.find_button);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);

        configureToolbar();
        configureToolbarUsernameField();
        configureToolbarFindButton();

        if (savedInstanceState == null) {
            configureFragments();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (!isTablet() && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();

            // update toolbar
            configureToolbar();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onUserFound(final User user) {
        try {
            final FlickrPhotoListFragment listFragment = (FlickrPhotoListFragment) getSupportFragmentManager()
                    .findFragmentByTag(FlickrPhotoListFragment.TAG);

            if (listFragment.onUserFound(user.getId())) {
                showProgressIndicator(getString(R.string.flickr_loading_user_public_photos));
            } else {
                showProgressIndicator(null);
            }
        } catch (final Exception ex) {
            showProgressIndicator(null);
        }
    }

    @Override
    public void onUserNotFound(final String message) {
        // hide progress indicator
        showProgressIndicator(null);

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
        showProgressIndicator(null);

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
        showProgressIndicator(null);
    }

    /**
     * Handles the situation when the user has selected a photo from the list.
     *
     * @param photoEntry PhotoEntry
     */
    public void onPhotoSelected(final PhotoEntry photoEntry) {
        // hide possibly opened keyboard
        ViewUtils.hideKeyboard(this);

        final FlickrPhotoDetailFragment fragment = FlickrPhotoDetailFragment.newInstance(photoEntry);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (isTablet()) {
            ft.replace(R.id.detail_fragment_container, fragment, FlickrPhotoDetailFragment.TAG);
        } else {
            ft.add(R.id.fragments_container, fragment, FlickrPhotoDetailFragment.TAG);
            ft.addToBackStack(FlickrPhotoListFragment.TAG);
        }

        ft.commit();
        getSupportFragmentManager().executePendingTransactions();

        // configure toolbar to update it if necessary
        configureToolbar();
    }

    /**
     * Configures the Toolbar that is inside the AppBarLayout.
     */
    public void configureToolbar() {
        if (!isTablet() && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            mToolbar.setTitle(getString(R.string.toolbar_photo_detail_fragment_title));
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    onBackPressed();
                }
            });
            mToolbar.setVisibility(View.VISIBLE);
            mUsernameInputLayout.setVisibility(View.GONE);
            mFindButton.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.GONE);
            mUsernameInputLayout.setVisibility(View.VISIBLE);
            mFindButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Configures the functionality of the Toolbar Username field.
     */
    private void configureToolbarUsernameField() {
        mUsernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // search for inputted username
                    searchUsername();
                }
                return true;
            }
        });
    }

    /**
     * Configure the functionality of the Find Button.
     */
    private void configureToolbarFindButton() {
        mFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // search for inputted username
                searchUsername();
            }
        });
    }

    /**
     * Adds the fragments necessary to populate the view.
     */
    private void configureFragments() {
        // add the list fragment to respective container
        final FlickrPhotoListFragment listFragment = new FlickrPhotoListFragment();
        final FragmentTransaction listFT = getSupportFragmentManager().beginTransaction();
        listFT.add(
                isTablet() ? R.id.list_fragment_container : R.id.fragments_container,
                listFragment,
                FlickrPhotoListFragment.TAG
        );
        listFT.commit();

        // check if necessary to add the detail fragment
        if (isTablet() && getSupportFragmentManager().getBackStackEntryCount() == 0) {
            final FlickrPhotoDetailFragment detailFragment = FlickrPhotoDetailFragment.newInstance(null);
            final FragmentTransaction detailFT = getSupportFragmentManager().beginTransaction();
            detailFT.replace(R.id.detail_fragment_container, detailFragment, FlickrPhotoDetailFragment.TAG);
            detailFT.commit();
        }
    }

    /**
     * Requests the {@link FlickrPresenter} to search for the given username.
     */
    private void searchUsername() {
        // hide keyboard
        ViewUtils.hideKeyboard(FlickrActivity.this);

        if (NetworkUtils.isNetworkConnected(this)) {
            if (!TextUtils.isEmpty(mUsernameEditText.getText())) {
                showProgressIndicator(getString(R.string.flickr_loading_user));
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
     * Utility method that shows or hides the progress indicator on the Toolbar, according if the message is null or not.
     *
     * @param message String
     */
    private void showProgressIndicator(@Nullable final String message) {
        mProgressIndicator.setVisibility(message == null ? View.GONE : View.VISIBLE);
        mFindButton.setVisibility(message == null ? View.VISIBLE : View.GONE);

        if (message != null) {
            mProgressIndicator.setText(message);
        }
    }
}
