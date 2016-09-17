package me.tiagomota.getflickred.ui.base.injection.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import me.tiagomota.getflickred.ui.base.injection.qualifiers.ApplicationContext;
import me.tiagomota.getflickred.ui.base.injection.modules.ApplicationModule;
import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.injection.DataModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DataModule.class
})
public interface ApplicationComponent {

    /**
     * Allows access, through the component interface, to the GetFlickrApplication context.
     *
     * @return Context
     */
    @ApplicationContext
    Context context();


    /**
     * Allows access, through the component interface, to the DataManager.
     *
     * @return DataManager
     */
    DataManager dataManager();
}
