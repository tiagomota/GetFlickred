package me.tiagomota.getflickred;

import android.app.Application;
import android.content.Context;

import me.tiagomota.getflickred.injection.components.ApplicationComponent;
import me.tiagomota.getflickred.injection.components.DaggerApplicationComponent;
import me.tiagomota.getflickred.injection.modules.ApplicationModule;

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


    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
