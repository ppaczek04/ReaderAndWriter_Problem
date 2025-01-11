package org.example.lab.agh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    private Library library;
    private Semaphore mutex;
    private Semaphore writ;
    private Semaphore queue;
    private Reader reader;

    @BeforeEach
    void setUp() {
        // Tworzymy wspólne zasoby do testów
        library = new Library(5, 3);
        mutex = new Semaphore(1);
        writ = new Semaphore(1);
        queue = new Semaphore(1);

        // Tworzymy instancję Reader do testowania
        reader = new Reader(1, mutex, writ, queue, library);
    }

    @Test
    void testReaderInitialization() {
        assertEquals(1, (int)reader.getIDofReader(), "Reader ID should be initialized correctly.");
        assertEquals(mutex, reader.getMutexByReader(), "Reader should have the correct mutex.");
        assertEquals(writ, reader.getWritByReader(), "Reader should have the correct writ semaphore.");
        assertEquals(queue, reader.getQueue(), "Reader should have the correct queue semaphore.");
        assertEquals(library, reader.getLibrary(), "Reader should be associated with the correct library.");
    }

    @Test
    void testSetAndGetIDofReader() {
        reader.setIDofReader(2);
        assertEquals((int)2, (int)reader.getIDofReader(), "Reader ID should be updated correctly.");
    }

    @Test
    void testSetAndGetMutex() {
        Semaphore newMutex = new Semaphore(1);
        reader.setMutexByReader(newMutex);
        assertEquals(newMutex, reader.getMutexByReader(), "Reader should use the updated mutex.");
    }

    @Test
    void testSetAndGetWrit() {
        Semaphore newWrit = new Semaphore(1);
        reader.setWritByReader(newWrit);
        assertEquals(newWrit, reader.getWritByReader(), "Reader should use the updated writ semaphore.");
    }

    @Test
    void testSetAndGetQueue() {
        Semaphore newQueue = new Semaphore(1);
        reader.setQueue(newQueue);
        assertEquals(newQueue, reader.getQueue(), "Reader should use the updated queue semaphore.");
    }

    @Test
    void testSetAndGetLibrary() {
        Library newLibrary = new Library(3, 2);
        reader.setLibrary(newLibrary);
        assertEquals(newLibrary, reader.getLibrary(), "Reader should be associated with the updated library.");
    }

    @Test
    void testRunAddsReaderToWaitingListAndImmediatelyToActiveList() throws InterruptedException {
        // Symulacja wątku czytelnika
        Thread readerThread = new Thread(reader);
        readerThread.start();

        // Oczekiwanie na dodanie do listy oczekujących
        Thread.sleep(100); // Krótkie opóźnienie na synchronizację

        synchronized (library) {
            assertTrue(library.activeReaders.contains(1), "Reader should be added to the waiting list.");
        }

        // Przerywamy wątek po zakończeniu testu
        readerThread.interrupt();
    }

    @Test
    void testRunAcquiresAndReleasesMutex() throws InterruptedException {
        // Symulacja wątku czytelnika
        Thread readerThread = new Thread(reader);
        readerThread.start();

        // Oczekiwanie na akcje czytelnika
        Thread.sleep(100); // Krótkie opóźnienie na synchronizację

        synchronized (library) {
            assertEquals(1, library.getReadcnt(), "Reader should increment the read count.");
            assertTrue(library.activeReaders.contains(1), "Reader should be in the active readers list.");
        }

        // Przerywamy wątek po zakończeniu testu
        readerThread.interrupt();
    }

}
