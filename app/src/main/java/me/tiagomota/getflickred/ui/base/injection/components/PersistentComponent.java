package me.tiagomota.getflickred.ui.base.injection.components;

import dagger.Component;
import me.tiagomota.getflickred.ui.base.injection.modules.ActivityModule;
import me.tiagomota.getflickred.ui.base.injection.modules.FragmentModule;
import me.tiagomota.getflickred.ui.base.injection.scope.PersistentScope;

/**
 * This component will live during an Activity lifecycle but it will survive configuration
 * changes. By using the {@link PersistentComponent} scope to annotate dependencies that need to
 * survive configuration changes (for example Presenters).
 */
@PersistentScope
@Component(dependencies = ApplicationComponent.class)
public interface PersistentComponent {

    /**
     * Adds the ActivityComponent to the Object Dependencies Graph.
     *
     * @param module ActivityModule
     * @return ActivityComponent
     */
    ActivityComponent plus(final ActivityModule module);

    /**
     * Adds the FragmentComponent to the Object Dependencies Graph.
     *
     * @param module FragmentModule
     * @return FragmentComponent
     */
    FragmentComponent plus(final FragmentModule module);

}
