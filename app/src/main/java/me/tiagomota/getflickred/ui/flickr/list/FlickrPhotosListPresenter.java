package me.tiagomota.getflickred.ui.flickr.list;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.model.Photo;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.ui.base.injection.scope.PersistentScope;
import me.tiagomota.getflickred.ui.base.mvp.BasePresenter;
import me.tiagomota.getflickred.utils.RxUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

@PersistentScope
class FlickrPhotosListPresenter extends BasePresenter<FlickrPhotosListView> {

    private static final int PER_PAGE = 5;

    private final DataManager mDataManager;
    private Subscription mSubscription;

    private List<PhotoEntry> mLoadedPhotos = new ArrayList<>();
    private int mNextPage = 1;

    @Inject
    FlickrPhotosListPresenter(final DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        RxUtils.cancel(mSubscription);
        super.detachView();
    }

    /**
     * Returns the list of already loaded photos.
     *
     * @return List
     */
    List<PhotoEntry> getLoadedPhotos() {
        return mLoadedPhotos;
    }

    /**
     * Fetch the next available page of photos of the given user.
     * Returns false if there is no more content to load.
     *
     * @param userId String
     * @return boolean
     */
    boolean loadUserPublicPhotos(final String userId) {
        if (mNextPage > 0) {
            final List<PhotoEntry> newPage = new ArrayList<>();
            mSubscription = mDataManager.getPublicPhotos(userId, mNextPage, PER_PAGE)
                    .flatMap(new Func1<PhotosList, Observable<Photo>>() {
                        @Override
                        public Observable<Photo> call(final PhotosList photosList) {
                            if (photosList.getPage() < photosList.getTotalPages()) {
                                mNextPage++; // update next page to load.
                            } else {
                                mNextPage = -1; // mark content all loaded.
                            }
                            return Observable.from(photosList.getPhotos());
                        }
                    })
                    .flatMap(new Func1<Photo, Observable<PhotoEntry>>() {
                        @Override
                        public Observable<PhotoEntry> call(final Photo photo) {
                            return Observable.zip(
                                    mDataManager.getPhotoInfo(photo.getId(), photo.getSecret()), // get the photo info
                                    mDataManager.getPhotoSizes(photo.getId()),                   // get the photo sizes
                                    new Func2<PhotoInfo, PhotoSize, PhotoEntry>() {
                                        @Override
                                        public PhotoEntry call(final PhotoInfo photoInfo, final PhotoSize photoSize) {
                                            return new PhotoEntry(photo, photoInfo, photoSize);  // merge the result in a UI obeject wrapper called PhotoEntry
                                        }
                                    });
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Action1<PhotoEntry>() {
                                @Override
                                public void call(final PhotoEntry photoEntry) {
                                    newPage.add(photoEntry);
                                }
                            },
                            new Action1<Throwable>() {
                                @Override
                                public void call(final Throwable throwable) {
                                    Log.e("PhotosListPresenter", "Error loading photos", throwable);
                                    getView().handleErrorLoadingNewPhotosPage(throwable.getMessage());
                                }
                            },
                            new Action0() {
                                @Override
                                public void call() {
                                    // update already loaded
                                    mLoadedPhotos.addAll(newPage);

                                    // request view to update UI
                                    if (mNextPage > 2) {
                                        getView().handleNewPhotosPageLoaded(newPage);
                                    } else {
                                        getView().handleFirstPhotosPageLoaded(newPage);
                                    }
                                }
                            });
            return true;
        } else {
            return false;
        }
    }
}
