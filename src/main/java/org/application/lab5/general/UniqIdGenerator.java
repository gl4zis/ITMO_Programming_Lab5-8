package org.application.lab5.general;

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
        int id = Math.abs(new Random(longId).nextInt());
        if (id == 0) return 1;
        else return id;
    }
}
