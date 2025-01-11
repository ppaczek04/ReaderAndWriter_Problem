package org.example.lab.agh;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantsTest {

    @Test
    void testTimeSpentReading() {
        assertEquals(1000, Constants.TIME_SPENT_READING, "TIME_SPENT_READING should be 1000 ms.");
    }

    @Test
    void testTimeSpentNotReading() {
        assertEquals(3000, Constants.TIME_SPENT_NOT_READING, "TIME_SPENT_NOT_READING should be 3000 ms.");
    }

    @Test
    void testTimeSpentWriting() {
        assertEquals(1000, Constants.TIME_SPENT_WRITING, "TIME_SPENT_WRITING should be 1000 ms.");
    }

    @Test
    void testTimeSpentNotWriting() {
        assertEquals(2000, Constants.TIME_SPENT_NOT_WRITING, "TIME_SPENT_NOT_WRITING should be 2000 ms.");
    }
}