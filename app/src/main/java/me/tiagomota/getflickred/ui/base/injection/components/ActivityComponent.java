package me.tiagomota.getflickred.ui.base.injection.components;

import dagger.Subcomponent;
import me.tiagomota.getflickred.ui.base.injection.modules.ActivityModule;
import me.tiagomota.getflickred.ui.base.injection.scope.ActivityScope;
import me.tiagomota.getflickred.ui.home.HomeActivity;
import me.tiagomota.getflickred.ui.myFlickr.MyFlickrActivity;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    /**
     * Allows all the Objects of the Dependency graph to be injected in the HomeActivity.
     *
     * @param activity HomeActivity
     */
    void inject(final HomeActivity activity);

    /**
     * Allows all the Objects of the Dependency graph to be injected in the MyFlickrActivity.
     *
     * @param activity MyFlickrActivity
     */
    void inject(final MyFlickrActivity activity);
}
