package me.tiagomota.getflickred.ui.flickr.list;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.util.RxSchedulersOverrideRule;
import rx.Observable;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
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


}
