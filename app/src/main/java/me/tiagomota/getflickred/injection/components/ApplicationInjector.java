package me.tiagomota.getflickred.injection.components;

import me.tiagomota.getflickred.injection.modules.ActivityModule;

/**
 * Interface to define the methods that will inject the app level dependencies,
 * into the object passed as parameter.
 */
interface ApplicationInjector {

    /**
     * Allows access to the Application level dependencies to the ActivityModule.
     *
     * @param activityModule {@link ActivityModule}
     * @return ActivityComponent
     */
    ActivityComponent plus(final ActivityModule activityModule);

}
