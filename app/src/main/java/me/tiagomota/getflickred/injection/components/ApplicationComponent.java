package me.tiagomota.getflickred.injection.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.injection.DataModule;
import me.tiagomota.getflickred.injection.annotations.ApplicationContext;
import me.tiagomota.getflickred.injection.modules.ApplicationModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DataModule.class
})
public interface ApplicationComponent extends ApplicationInjector {

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
