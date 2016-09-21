package me.tiagomota.getflickred.ui.base.injection.components;


import dagger.Subcomponent;
import me.tiagomota.getflickred.ui.base.injection.modules.FragmentModule;
import me.tiagomota.getflickred.ui.base.injection.scope.FragmentScope;
import me.tiagomota.getflickred.ui.flickr.detail.FlickrPhotoDetailFragment;
import me.tiagomota.getflickred.ui.flickr.list.FlickrPhotoListFragment;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    /**
     * Allows all the Objects of the Dependency graph to be injected in the {@link FlickrPhotoListFragment}.
     *
     * @param fragment FlickrPhotoListFragment
     */
    void inject(final FlickrPhotoListFragment fragment);

    /**
     * Allows all the Objects of the Dependency graph to be injected in the {@link FlickrPhotoDetailFragment}.
     *
     * @param fragment FlickrPhotoDetailFragment
     */
    void inject(final FlickrPhotoDetailFragment fragment);
}
