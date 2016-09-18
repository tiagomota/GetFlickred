package me.tiagomota.getflickred.ui.base.injection.components;


import dagger.Subcomponent;
import me.tiagomota.getflickred.ui.base.injection.modules.FragmentModule;
import me.tiagomota.getflickred.ui.base.injection.scope.FragmentScope;
import me.tiagomota.getflickred.ui.flickr.list.FlickrPhotosListFragment;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    /**
     * Allows all the Objects of the Dependency graph to be injected in the {@link FlickrPhotosListFragment}.
     *
     * @param fragment MyFlickrPhotosListFragment
     */
    void inject(final FlickrPhotosListFragment fragment);
}
