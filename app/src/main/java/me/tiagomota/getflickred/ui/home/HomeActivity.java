package me.tiagomota.getflickred.ui.home;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.ui.base.BaseActivity;
import me.tiagomota.getflickred.ui.flickr.FlickrActivity;
import me.tiagomota.getflickred.utils.NetworkUtils;
import me.tiagomota.getflickred.utils.SnackBarFactory;
import me.tiagomota.getflickred.utils.ViewUtils;

public class HomeActivity extends BaseActivity
    implements HomeView {

    @Inject
    HomePresenter mPresenter;

    // General
    private CoordinatorLayout mCoordinatorLayout;

    // Username field
    private TextInputLayout mUsernameTextInputLayout;
    private EditText mUsernameEditText;

    // Submit button
    private Button mSubmitBtn;

    // Progress Bar
    private ProgressBar mProgressBar;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void mapLayoutViews() {
        // General
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        // Username field
        mUsernameTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_username);
        mUsernameEditText = (EditText) findViewById(R.id.edit_text_username);

        // Submit button
        mSubmitBtn = (Button) findViewById(R.id.btn_submit);

        // ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject the dependencies here to be able to use the Presenter
        getActivityComponent().inject(this);
        // attach this view to the Presenter
        mPresenter.attachView(this);

        // setup layout views
        configureUsernameField();
        configureSubmitButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * Configures the Username field views functionality.
     */
    private void configureUsernameField() {
        mUsernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    retrieveUser();
                }
                return true;
            }
        });
    }

    /**
     * Configures the submit method functionality.
     */
    private void configureSubmitButton() {
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveUser();
            }
        });
    }


    /**
     * Attempts to request the Presenter to retrieve the user by the username inputed on the Username Field.
     */
    private void retrieveUser() {
        ViewUtils.hideKeyboard(this); // hide soft keyboard

        if (NetworkUtils.isNetworkConnected(HomeActivity.this)) {

            if (!TextUtils.isEmpty(mUsernameEditText.getText())) {
                mUsernameTextInputLayout.setError(null);

                mProgressBar.setVisibility(View.VISIBLE);
                mSubmitBtn.setVisibility(View.GONE);

                mPresenter.findUser(mUsernameEditText.getText().toString());

            } else {
                mUsernameTextInputLayout.setError(getString(R.string.error_field_empty));
            }

        } else {
            SnackBarFactory.build(
                    mCoordinatorLayout,
                    getString(R.string.error_no_network),
                    getString(R.string.retry_action),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            retrieveUser();
                        }
                    }).show();
        }
    }

    @Override
    public void onSuccessFindingUser(final User user) {
        FlickrActivity.newInstance(this, user); // start flickr activity

        mProgressBar.setVisibility(View.GONE);
        mSubmitBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorFindingUser(final String message) {
        SnackBarFactory.build(
                mCoordinatorLayout, message, getString(R.string.retry_action),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retrieveUser();
                    }
                }).show();

        mProgressBar.setVisibility(View.GONE);
        mSubmitBtn.setVisibility(View.VISIBLE);
    }
}
