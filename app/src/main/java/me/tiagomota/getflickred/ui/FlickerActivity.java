package me.tiagomota.getflickred.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.ui.base.BaseActivity;

public class FlickerActivity extends BaseActivity {

    private TextInputLayout mUsernameTextInputLayout;
    private EditText mUsernameEditText;
    private Button mFindBtn;

    @Inject
    DataManager mDataManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_flicker);

        findViews();

        configureFindButton();
    }


    private void findViews() {
        mUsernameTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_username);
        mUsernameEditText = (EditText) findViewById(R.id.edit_text_username);
        mFindBtn = (Button) findViewById(R.id.btn_find);
    }

    private void configureFindButton() {
        mFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mUsernameTextInputLayout.setError(null); // clear error

                final CharSequence username = mUsernameEditText.getText();
                if (username != null && !TextUtils.isEmpty(username)) {
                    mDataManager.fetchUser(username.toString());
                } else {
                    mUsernameTextInputLayout.setError("Username field cannot be blank!");
                }
            }
        });
    }
}
