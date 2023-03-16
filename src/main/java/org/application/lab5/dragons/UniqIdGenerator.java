package org.application.lab5.dragons;

import java.util.Date;
import java.util.Random;

/**
 * Generator unique numbers for dragon id
 */

public abstract class UniqIdGenerator {

    /**
     * Returns positive integer random number.
     * Random based on timestamp
     *
     * @return id
     */
    public static int getIntId() {
        long longId = new Date().getTime();
        return Math.abs(new Random(longId).nextInt());
    }
}
