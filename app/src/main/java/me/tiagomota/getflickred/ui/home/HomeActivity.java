package me.tiagomota.getflickred.ui.home;

import android.content.Intent;
import android.os.Bundle;
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
import me.tiagomota.getflickred.ui.base.injection.components.ActivityComponent;
import me.tiagomota.getflickred.ui.myFlickr.MyFlickrActivity;
import me.tiagomota.getflickred.utils.NetworkUtils;

public class HomeActivity extends BaseActivity
    implements HomeView {

    @Inject
    HomePresenter mPresenter;

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
        getActivityComponent().inject(this); // inject the dependencies here to be able to use the Presenter
        mPresenter.attachView(this);

        configureSubmitButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * Configures the submit method functionality.
     */
    private void configureSubmitButton() {
        mUsernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    findUser();
                }
                return true;
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUser();
            }
        });
    }


    private void findUser() {
        if (NetworkUtils.isNetworkConnected(HomeActivity.this)) {

            if (!TextUtils.isEmpty(mUsernameEditText.getText())) {
                mUsernameTextInputLayout.setError(null);

                mProgressBar.setVisibility(View.VISIBLE);
                mSubmitBtn.setVisibility(View.GONE);
                mPresenter.findUser(mUsernameEditText.getText().toString());
            } else {
                mUsernameTextInputLayout.setError("Username cannot be empty!");
            }

        } else {
            // TODO show no network error message in snackbar
        }
    }

    @Override
    public void onSuccessFindingUser(final User user) {
        final Intent intent = new Intent(this, MyFlickrActivity.class);
        startActivity(intent);

        mProgressBar.setVisibility(View.GONE);
        mSubmitBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorFindingUser(final String message) {
        mUsernameTextInputLayout.setError(message);
        mProgressBar.setVisibility(View.GONE);
        mSubmitBtn.setVisibility(View.VISIBLE);
    }
}
