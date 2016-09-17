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
}
