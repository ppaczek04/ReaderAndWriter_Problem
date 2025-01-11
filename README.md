# Description
Welcome to **Readers and Writers problem solution**  which is a classic problem of multiple threads sharing an access to a shared resource.
The Readers-Writers problem is a classical synchronization problem in computer science. It deals with managing access to 
a shared resource (e.g., a library) by multiple threads, ensuring data consistency. In this implementation, readers can 
access the resource concurrently, but writers require exclusive access, blocking both readers and other writers.
To solve the problem, semaphores and synchronized blocks are used to manage the queues of waiting readers and writers, as well as the active ones.
This implementation prioritizes fairness, ensuring that neither readers nor writers are starved by maintaining proper queues and releasing locks as necessary.
The solution also includes unit tests to verify functionality and coverage.

- The **writerSemaphore** semaphore ensures that only one writer at a time can use library, and no reader will be allowed. It also ensures that when there is at least one reader in library, writer won't enter as he needs library exclusively for him. 
- The **readerSemaphore** semaphore ensures that readers enter library at once, so that limit of 5 readers in the library
isn't exceeded.  
- The **queue** semaphore ensures that no thread will be starved (in the long enough time the thread will access library eventually)
by creating FIFO queue (First In First Out) for Threads. So when thread leaves the library, it enters queue form the back and waits for
its turn.

**Hope you find my problem-solution git implementation helpful!**

# Usage tutorial
To see the logs of readers and writers objects performing actions on a shared resource 
(which is library object) run `main/src/main/java/org.example.lab.agh/Main` file.


# Documentation and SonarQube report:
Project documentation is saved in: `Javadoc/target/site/apidocs/index.html` file.  
SonarQube report in PDF format is saved in `sonar-cube/reportSonarQube.pdf` file.

# Tools used
Whole project was built using Maven Tool.
Others tools used are:
- SonarQube
- Jacoco
- Junit Jupiter with Mockito library
- Javadoc

# Credits
- Contributor: [ppaczek04](https://github.com/ppaczek04)
