package me.tiagomota.getflickred.ui.flickr;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.TestDataFactory;
import me.tiagomota.getflickred.base.BaseUiTest;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.data.model.User;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FlickrActivityTest extends BaseUiTest {

    @Test
    public void testOnUserFound() {
        final User user = TestDataFactory.get().makeUser();
        final PhotosList photosList = TestDataFactory.get().makePhotosList(10);
        photosList.setTotalPages(1);
        final PhotoInfo info = TestDataFactory.get().makePhotoInfo();
        final PhotoSize size = TestDataFactory.get().makePhotoSize();

        when(mComponent.getMockDataManager().getUser(anyString())).thenReturn(Observable.just(user));
        when(mComponent.getMockDataManager().getPublicPhotos(anyString(), anyInt(), anyInt())).thenReturn(Observable.just(photosList));
        when(mComponent.getMockDataManager().getPhotoInfo(anyString(), anyString())).thenReturn(Observable.just(info));
        when(mComponent.getMockDataManager().getPhotoSizes(anyString())).thenReturn(Observable.just(size));

        onView(withId(R.id.progress_indicator)).check(matches(not(isDisplayed())));
        onView(withId(R.id.edit_text_username)).perform(typeText("test"));
        onView(withId(R.id.find_button)).perform(click());

        onView(withId(R.id.progress_indicator)).check(matches(isDisplayed()));
    }


}
