package me.tiagomota.getflickred.ui.base.injection.components;


import dagger.Subcomponent;
import me.tiagomota.getflickred.ui.base.BaseFragment;
import me.tiagomota.getflickred.ui.base.injection.modules.FragmentModule;
import me.tiagomota.getflickred.ui.base.injection.scope.FragmentScope;
import me.tiagomota.getflickred.ui.myFlickr.list.MyFlickrPhotosListFragment;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    /**
     * Allows all the Objects of the Dependency graph to be injected in the {@link MyFlickrPhotosListFragment}.
     *
     * @param fragment MyFlickrPhotosListFragment
     */
    void inject(final MyFlickrPhotosListFragment fragment);
}
