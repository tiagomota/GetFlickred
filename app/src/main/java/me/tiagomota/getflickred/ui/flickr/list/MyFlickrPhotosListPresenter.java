package me.tiagomota.getflickred.ui.flickr.list;

import javax.inject.Inject;

import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.ui.base.injection.scope.PersistentScope;
import me.tiagomota.getflickred.ui.base.mvp.BasePresenter;

@PersistentScope
class MyFlickrPhotosListPresenter extends BasePresenter<MyFlickrPhotosListView> {

    private final DataManager mDataManager;


    @Inject
    MyFlickrPhotosListPresenter(final DataManager dataManager) {
        mDataManager = dataManager;
    }
}
