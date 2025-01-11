package org.example.lab.agh;

/**
 * The {@code Constants} class defines constant values used throughout the application.
 * These constants specify the time durations for reading, writing, and waiting.
 * All time values are specified in milliseconds.
 */
public class Constants {

    private Constants(){

    }

    /**
     * The time spent reading by a reader in milliseconds.
     */
    public static final int TIME_SPENT_READING = 1000;

    /**
     * The time spent not reading (idle time) by a reader in milliseconds.
     */
    public static final int TIME_SPENT_NOT_READING = 3000;

    /**
     * The time spent writing by a writer in milliseconds.
     */
    public static final int TIME_SPENT_WRITING = 1000;

    /**
     * The time spent not writing (idle time) by a writer in milliseconds.
     */
    public static final int TIME_SPENT_NOT_WRITING = 2000;
}
