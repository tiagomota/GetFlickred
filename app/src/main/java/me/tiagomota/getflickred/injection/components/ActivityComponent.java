package me.tiagomota.getflickred.injection.components;

import dagger.Subcomponent;
import me.tiagomota.getflickred.injection.annotations.ActivityScope;
import me.tiagomota.getflickred.injection.modules.ActivityModule;
import me.tiagomota.getflickred.ui.FlickerActivity;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(final FlickerActivity activity);
}
