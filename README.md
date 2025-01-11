# Description
Welcome to **Textual Hotel Manager**  Maven app which takes care of checkin-checkout guests operations.
Our app enables text-service for customers who want to choose and book a room in our hotel.
Main objects of our app are: hotel, guest, room.   Configuration of hotel and its available rooms is loaded from dedicated Excel (.xlsx) file.
Guests-related data and their booked rooms info is stored in second dedicated Excel room.  
**Enjoy using my application!**

# Usage tutorial
Our app starts by running file **App** from `main/src/main/java/App` directory.  
Hotel configuration (that can be customised) is automatically loaded
from file: `main/src/main/resources/hotel_data.xlsx`.  
Data about current hotel guests and room availability is automatically loaded
from file: `main/src/main/resources/guests_state.xlsx`. 


# Documentation and SonarQube report:
Project documentation is saved in: `Javadoc/target/site/apidocs/index.html` file.  
SonarQube report in PDF format is saved in `sonar-cube/reportSonarQube.pdf` file.

# Tools used
Whole project was built using Maven Tool.
Others tools which were used include:
- SonarQube
- Jacoco
- Junit Jupiter with Mockito library
- Javadoc

# Credits
- Contributor: [ppaczek04](https://github.com/ppaczek04)
