package org.example.lab.agh;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class LibraryTest {

    private Library library;

    @BeforeEach
    public void setUp() {
        library = new Library(5, 3);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        assertEquals(5, library.getNumReaders(), "Number of readers should be initialized correctly.");
        assertEquals(3, library.getNumWriters(), "Number of writers should be initialized correctly.");
        assertEquals(0, library.getReadcnt(), "Initial count of active readers should be 0.");
        assertTrue(library.waitingReaders.isEmpty(), "Waiting readers list should be empty initially.");
        assertTrue(library.waitingWriters.isEmpty(), "Waiting writers list should be empty initially.");
        assertTrue(library.activeReaders.isEmpty(), "Active readers list should be empty initially.");
        assertTrue(library.activeWriters.isEmpty(), "Active writers list should be empty initially.");
    }

    @Test
    void testStartReadingAddsReaderToActiveList() {
        Reader reader = new Reader(1, null, null, null, library);
        library.waitingReaders.add(1);

        library.startReading(reader);

        assertTrue(library.activeReaders.contains(1), "Reader should be added to active readers.");
        assertFalse(library.waitingReaders.contains(1), "Reader should be removed from waiting readers.");
    }

    @Test
    void testStopReadingRemovesReaderFromActiveList() {
        Reader reader = new Reader(1, null, null, null, library);
        library.activeReaders.add(1);

        library.stopReading(reader);

        assertFalse(library.activeReaders.contains(1), "Reader should be removed from active readers.");
    }

    @Test
    void testStartWritingAddsWriterToActiveList() {
        Writer writer = new Writer(1, null, null, null, library);
        library.waitingWriters.add(1);

        library.startWriting(writer);

        assertTrue(library.activeWriters.contains(1), "Writer should be added to active writers.");
        assertFalse(library.waitingWriters.contains(1), "Writer should be removed from waiting writers.");
    }

    @Test
    void testStopWritingRemovesWriterFromActiveList() {
        Writer writer = new Writer(1, null, null, null, library);
        library.activeWriters.add(1);

        library.stopWriting(writer);

        assertFalse(library.activeWriters.contains(1), "Writer should be removed from active writers.");
    }

    @Test
    void testSetAndGetReadcnt() {
        library.setReadcnt(3);
        assertEquals(3, library.getReadcnt(), "Read count should be updated correctly.");
    }

    @Test
    void testSetAndGetNumReaders() {
        library.setNumReaders(10);
        assertEquals(10, library.getNumReaders(), "Number of readers should be updated correctly.");
    }

    @Test
    void testSetAndGetNumWriters() {
        library.setNumWriters(7);
        assertEquals(7, library.getNumWriters(), "Number of writers should be updated correctly.");
    }

    @Test
    void testRunAppInitializesThreads() {
        Library testLibrary = new Library(2, 2);

        // Override System.out to capture output for verification
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        testLibrary.runApp();

        String output = outputStream.toString();

        // Assert that readers and writers are initialized
        assertTrue(output.contains("**Reader 1. initiated**"), "Reader 1 should be initialized.");
        assertTrue(output.contains("**Reader 2. initiated**"), "Reader 2 should be initialized.");
        assertTrue(output.contains("**Writer 1. initiated**"), "Writer 1 should be initialized.");
        assertTrue(output.contains("**Writer 2. initiated**"), "Writer 2 should be initialized.");

        // Restore System.out
        System.setOut(System.out);
    }
}