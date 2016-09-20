package me.tiagomota.getflickred;

import android.app.Application;
import android.content.Context;

import me.tiagomota.getflickred.ui.base.injection.components.ApplicationComponent;
import me.tiagomota.getflickred.ui.base.injection.components.DaggerApplicationComponent;
import me.tiagomota.getflickred.ui.base.injection.modules.ApplicationModule;

public class GetFlickredApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    /**
     * Returns the current instance of the GetFlickredApplication.
     *
     * @param context Context
     * @return GetFlickredApplication
     */
    public static GetFlickredApplication get(final Context context) {
        return (GetFlickredApplication) context.getApplicationContext();
    }

    /**
     * Returns an alive instance of the ApplicationComponent or returns a new one.
     *
     * @return ApplicationComponent
     */
    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
