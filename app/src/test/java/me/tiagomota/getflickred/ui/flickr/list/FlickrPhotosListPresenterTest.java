package me.tiagomota.getflickred.ui.flickr.list;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import me.tiagomota.getflickred.TestDataFactory;
import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.model.Photo;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.ui.flickr.PhotoEntry;
import me.tiagomota.getflickred.util.RxSchedulersOverrideRule;
import rx.Observable;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FlickrPhotosListPresenterTest {

    private FlickrPhotosListView mListView;
    private DataManager mDataManager;
    private FlickrPhotosListPresenter mPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mListView = mock(FlickrPhotosListView.class);
        mDataManager = mock(DataManager.class);
        mPresenter = new FlickrPhotosListPresenter(mDataManager);
        mPresenter.attachView(mListView);
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }

    @Test
    public void handleUserFoundTest() {
        final Throwable mockError = mock(Throwable.class);
        when(mockError.getMessage()).thenReturn("");
        when(mDataManager.getPublicPhotos(anyString(), anyInt(), anyInt())).thenReturn(
                Observable.<PhotosList>error(mockError)
        );

        // test user not stored yet, so trigger loading
        assertTrue(mPresenter.handleUserFound("test"));

        // test user already stored so no loading triggered
        assertFalse(mPresenter.handleUserFound("test"));
    }

    @Test
    public void getLoadedPhotosTest() {
        assertNotNull(mPresenter.getLoadedPhotos());
        assertTrue(mPresenter.getLoadedPhotos().isEmpty());
    }

    @Test
    public void loadUserPublicPhotosErrorTest() {
        final Throwable mockError = mock(Throwable.class);
        when(mockError.getMessage()).thenReturn("");
        when(mDataManager.getPublicPhotos(anyString(), anyInt(), anyInt())).thenReturn(
                Observable.<PhotosList>error(mockError)
        );

        mPresenter.loadUserPublicPhotos(false);
        verify(mListView).handleErrorLoadingNewPhotosPage(anyString());
        verify(mListView, never()).handleNewPhotosPageLoaded(anyListOf(PhotoEntry.class));
        verify(mListView, never()).handleFirstPhotosPageLoaded(anyListOf(PhotoEntry.class));
    }

    @Test
    public void loadUserPublicPhotosSuccessTest() {
        // prepare data
        final Photo photo = TestDataFactory.get().makePhoto();
        final List<Photo> photos = new ArrayList<>();
        photos.add(photo);
        final PhotoInfo photoInfo = TestDataFactory.get().makePhotoInfo();
        final PhotoSize photoSize = TestDataFactory.get().makePhotoSize();

        // prepare PhotoList object
        final PhotosList photosList = mock(PhotosList.class);
        when(photosList.getPage()).thenReturn(1);
        when(photosList.getTotalPages()).thenReturn(2);
        when(photosList.getPhotos()).thenReturn(photos);

        // set mock redirects of DataManger
        when(mDataManager.getPublicPhotos(anyString(), anyInt(), anyInt())).thenReturn(Observable.just(photosList));
        when(mDataManager.getPhotoInfo(anyString(), anyString())).thenReturn(Observable.just(photoInfo));
        when(mDataManager.getPhotoSizes(anyString())).thenReturn(Observable.just(photoSize));

        // execute method with reboot mode to fetch first page
        mPresenter.loadUserPublicPhotos(true);

        // verify that the first page method callback is called
        verify(mListView).handleFirstPhotosPageLoaded(anyListOf(PhotoEntry.class));
        verify(mListView, never()).handleNewPhotosPageLoaded(anyListOf(PhotoEntry.class));
        verify(mListView, never()).handleErrorLoadingNewPhotosPage(anyString());

        // execute method without reboot mode to fetch second page
        when(photosList.getPage()).thenReturn(2);
        mPresenter.loadUserPublicPhotos(false);

        // verify that the first page method callback is NOT called
        verify(mListView).handleNewPhotosPageLoaded(anyListOf(PhotoEntry.class));
        verify(mListView, times(1)).handleFirstPhotosPageLoaded(anyListOf(PhotoEntry.class));
        verify(mListView, never()).handleErrorLoadingNewPhotosPage(anyString());

        // assert that the load method returns false because there is no more content to load
        assertFalse(mPresenter.loadUserPublicPhotos(false));
    }
}
