package me.tiagomota.getflickred.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import me.tiagomota.getflickred.GetFlickredApplication;
import me.tiagomota.getflickred.ui.base.injection.components.ActivityComponent;
import me.tiagomota.getflickred.ui.base.injection.components.DaggerPersistentComponent;
import me.tiagomota.getflickred.ui.base.injection.components.PersistentComponent;
import me.tiagomota.getflickred.ui.base.injection.modules.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    private static final SparseArrayCompat<PersistentComponent> sStoredComponents = new SparseArrayCompat<>();

    private int mActivityId;
    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached PersistentComponent if this is
        // being called after a configuration change.
        if (savedInstanceState != null) {
            mActivityId = savedInstanceState.getInt(KEY_ACTIVITY_ID);
        } else {
            mActivityId = NEXT_ID.getAndIncrement();
        }

        final PersistentComponent persistentComponent;
        if (sStoredComponents.get(mActivityId) == null) {
            Log.i("BaseActivity", "Creating new PersistentComponent id=" + mActivityId);
            persistentComponent = DaggerPersistentComponent.builder()
                    .applicationComponent(GetFlickredApplication.get(this).getComponent())
                    .build();
            sStoredComponents.put(mActivityId, persistentComponent);
        } else {
            Log.i("BaseActivity", "Reusing PersistentComponent id=" + mActivityId);
            persistentComponent = sStoredComponents.get(mActivityId);
        }

        // inject to the activity the dependencies graph
        mActivityComponent = persistentComponent.plus(new ActivityModule(this));

        // set content view
        setContentView(getActivityLayout());

        // map layout views
        mapLayoutViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        // destroy persistent component if is not a configuration change
        if (!isChangingConfigurations()) {
            sStoredComponents.remove(mActivityId);
        }
        super.onDestroy();
    }

    /**
     * Getter for this activity associated PersistentComponent.
     *
     * @return PersistentComponent
     */
    PersistentComponent getPersistentComponent() {
        return sStoredComponents.get(mActivityId);
    }

    /**
     * Getter of the currently active ActivityComponent.
     *
     * @return ActivityComponent
     */
    protected ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    /**
     * Returns the chosen layout for this activity.
     *
     * @return int
     */
    protected abstract @LayoutRes int getActivityLayout();

    /**
     * Requires the activity to map the necessary layout views
     * to instance variables, for better organization.
     */
    protected abstract void mapLayoutViews();
}
