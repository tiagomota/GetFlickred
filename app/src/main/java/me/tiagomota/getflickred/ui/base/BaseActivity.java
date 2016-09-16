package me.tiagomota.getflickred.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import me.tiagomota.getflickred.GetFlickredApplication;
import me.tiagomota.getflickred.injection.components.ActivityComponent;
import me.tiagomota.getflickred.injection.modules.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = GetFlickredApplication.get(this)
                .getComponent()
                .plus(new ActivityModule());
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }
}
