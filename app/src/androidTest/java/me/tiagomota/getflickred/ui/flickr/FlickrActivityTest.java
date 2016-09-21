package me.tiagomota.getflickred.ui.flickr;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.TestDataFactory;
import me.tiagomota.getflickred.base.BaseUiTest;
import me.tiagomota.getflickred.data.model.Photo;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.matchers.RecyclerViewMatcher;
import me.tiagomota.getflickred.matchers.TextInputLayoutMatcher;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FlickrActivityTest extends BaseUiTest {

    @Test
    public void testFindUsernameEmpty() {
        onView(withId(R.id.empty_container)).check(matches(isDisplayed()));
        onView(withId(R.id.empty_container)).check(matches(hasDescendant(withId(R.id.empty_image))));
        onView(withId(R.id.find_button)).perform(click());

        final String expectedError = InstrumentationRegistry.getTargetContext().getString(R.string.error_field_empty);
        onView(withId(R.id.text_input_layout_username)).check(
                matches(TextInputLayoutMatcher.withErrorInInputLayout(expectedError))
        );
    }

    @Test
    public void testOnUserFirstPhotosPageLoaded() {
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

        int position = 0;
        String title;
        for (final Photo photo : photosList.getPhotos()) {
            title = photo.getTitle();
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(position))
                    .check(matches(hasDescendant(withText(title))))
                    .check(matches(isDisplayed()))
                    .check(matches(isClickable()));
            position++;
        }

        onView(withId(R.id.empty_container)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testOnUserNotFound() {
        final User user = mock(User.class);
        when(user.getId()).thenReturn("test");
        when(user.isStatusSuccess()).thenReturn(false);
        when(mComponent.getMockDataManager().getUser(anyString())).thenReturn(Observable.just(user));

        // search for user
        onView(withId(R.id.edit_text_username)).perform(typeText("test"));
        onView(withId(R.id.find_button)).perform(click());

        // check empty screen is displayed and the snackbar showed
        onView(withId(R.id.empty_container)).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()));
        onView(allOf(withId(android.support.design.R.id.snackbar_action), withText(R.string.action_retry)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testOnUserWithoutPhotos() {
        final User user = TestDataFactory.get().makeUser();
        final PhotosList photosList = TestDataFactory.get().makePhotosList(0);

        when(mComponent.getMockDataManager().getUser(anyString())).thenReturn(Observable.just(user));
        when(mComponent.getMockDataManager().getPublicPhotos(anyString(), anyInt(), anyInt())).thenReturn(Observable.just(photosList));

        onView(withId(R.id.edit_text_username)).perform(typeText("test"));
        onView(withId(R.id.find_button)).perform(click());

        // check empty screen is displayed and the snackbar showed
        onView(withId(R.id.empty_container)).check(matches(isDisplayed()));
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.error_user_without_photos)))
                .check(matches(isDisplayed()));
        onView(allOf(withId(android.support.design.R.id.snackbar_action), withText(R.string.action_close)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testOnPhotoSelected() {
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

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check the detail area is showing
        onView(withId(R.id.detail_container)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_container))
                .check(matches(hasDescendant(withId(R.id.image))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.detail_container))
                .check(matches(hasDescendant(withId(R.id.title))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.detail_container))
                .check(matches(hasDescendant(withId(R.id.description))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.detail_container))
                .check(matches(hasDescendant(withId(R.id.description_label))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.detail_container))
                .check(matches(hasDescendant(withId(R.id.tags_comments_container))))
                .check(matches(isDisplayed()));
        onView(withId(R.id.detail_container))
                .check(matches(hasDescendant(withId(R.id.dates_container))))
                .check(matches(isDisplayed()));

    }
}
