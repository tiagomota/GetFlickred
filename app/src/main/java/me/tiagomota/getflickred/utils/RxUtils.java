package me.tiagomota.getflickred.utils;

import rx.Subscription;

public final class RxUtils {

    /**
     * Given a request (observable) subscription, it cancels it.
     *
     * @param subscription Subscription
     */
    public static void cancel(final Subscription subscription) {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * Given a request (observable) subscription, check if it is on going or not.
     *
     * @param subscription {@link Subscription}
     * @return boolean
     */
    public static boolean onGoing(final Subscription subscription) {
        return subscription != null && !subscription.isUnsubscribed();
    }
}
