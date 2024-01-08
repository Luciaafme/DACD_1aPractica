# Final Project: Travel Planner

Lucía Afonso Medina

University of Las Palmas de Gran Canaria

2nd year in Engineering and Data science 

Subject: Data science application development

School of computer engineering

# Functionality


This project integrates information on accommodation options and meteorological forecasts for the 8 canary islands. 

The "weather-provider" module is designed to regularly retrieve meteorological data from the OpenWeather API for these islands over the next 5 days. This retrieval occurs precisely at 12 p.m. daily, with updates every 6 hours. Subsequently, the module transmits this data to the "prediction.Weather'' topic. In parallel, the "Accommodation-provider" module is responsible for obtaining nightly prices from various hotels for check-ins through the Xotelo API. It operates with a frequency of 6 hours and sends this information to the "prediction.Booking'' topic.

Additionally, the "datamart-store-builder" module manages the organized temporal storage of events sourced from the broker. It subscribes to the relevant topic, serializing events in the "datalake" directory following a specific structure: "datalake/eventstore/topic/{ss}/{YYYYMMDD}.events." Here, YYYYMMDD represents the year, month, and day derived from the event's timestamp, while ".events" serves as the file extension for storing events associated with a specific day.

Furthermore, the travel-planner module with a user interface, responsible for creating datamarts with relevant information. This enables the offering of diverse stay options on an island to users, along with accurate meteorological predictions for those specific days.


API OpenWeatherMap--> https://openweathermap.org/forecast5

API Xotelo --> https://xotelo.com/


# How to run the program

This process involves downloading, extracting, and running two modules event-store-builder and weather-provider. The first module is responsible for creating the "eventstore" directory at a specified location, and the second module requires your API key as a         parameter for execution.

You should follow these steps: 
1. Install and Activate the broker ActiveMq in your system.
2. Download the ZIP files from the latest release.
3. Unzip the contents of each ZIP file to the location of your choice.
4. Run the datalake-builder module:
   >Execute the datalake-builder module from its location within your system. Use the provided image as a reference, passing the desired path as a parameter to create the "datalake" directory. As an example, we want to create this directory on the Desktop.

![image](https://github.com/Luciaafme/DACD_Final_Project/assets/145342904/2a854033-ebd0-4cbc-b64d-4a343dce051b)

5. Now, run the travel-planner module:
   > Execute the travel- planner module from its location within your system. Use the provided image as a reference, passing the desired path as a parameter to create the database. As an example, we want to create a database called database.db on the Desktop on a directory called "jdbc".

![image](https://github.com/Luciaafme/DACD_Final_Project/assets/145342904/11b97f40-e01d-4616-908b-aed43d4be07f)


6. Continue with the accommodation-provider module (PLEASE, READ THE NEXT SECTION 'Things to take into account'):
   
   > Run the accommodation-provider module from its location within your system. 

![image](https://github.com/Luciaafme/DACD_Final_Project/assets/145342904/8549b2c1-ae29-4902-8886-bb8c0d116534)


7. Next, run the weather-provider module:
   >Execute the weather-provider module from its location within your system, following the same approach as in the previous step. This time, pass your API key as a parameter.


![image](https://github.com/Luciaafme/DACD_2aPractica/assets/145342904/eefd6b10-99be-4834-a1ea-0113d9c6569e)

# Things to take into account
The Xotelo API is noticeably slow, which can occasionally lead to timeout issues. In most cases, simply waiting is sufficient to resolve this error. In very rare instances, it may be necessary to rerun the Accommodation Provider module to ensure its continuous functionality. The user interface may remain operational even in the absence of data from this API; however, it will display a message indicating that no data is currently available and encourage the user to try again. On the other hand, error messages will be displayed if attempts are made to input more days than allowed or if a reservation is made for today after 12:00:00 PM.

![image](https://github.com/Luciaafme/DACD_Final_Project/assets/145342904/a44ac7fd-cc90-49ab-a4f3-03be6579af8d)




# Resources used
The development environment used was IntelliJ IDEA, a widely used and highly integrated IDE. This IDE is connected to various tools and technologies to facilitate software development. One of the version control systems used was Git, which allows detailed tracking of changes in the source code. In addition, GitHub was used as a cloud hosting service to store the different Git repositories.

For dependency management and project build, Maven, a build automation tool that simplifies the process of compiling, testing and managing project dependencies, was used. In this project the dependicies that were used are:

Jsoup 

    <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.16.2</version>
        </dependency>

Gson

        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>

ActiveMQ

            <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-client</artifactId>
            <version>5.15.2</version>
        </dependency>
Http3

      <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>5.0.0-alpha.11</version> 
        </dependency>
SqLite

      <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.34.0</version>
         </dependency>
         
Jcalendar 

         <dependency>
            <groupId>com.toedter</groupId>
            <artifactId>jcalendar</artifactId>
            <version>1.4</version>
        </dependency> 

Finally, for documentation, Markdown, a plain text formatting syntax that facilitates the creation of readable and well-structured documents, was used. Markdown is widely used for writing technical documentation and READMEs in GitHub repositories, due to its simplicity and readability.




    
# Design

The application design follows the principles of object-oriented design and utilizes the Model-View-Controller (MVC) architectural pattern to separate concerns and maintain modular and easily maintainable code. 

In this particular application, the decision to create four modules, "weather-provider", "booking-privider", "datalake-builder," and "travel-planner" suggests a modular and component-based approach. Let's break down the reasons for creating these modules:

![image](https://github.com/Luciaafme/DACD_2aPractica/assets/145342904/0f8cf336-de0a-4893-8e16-1422553508d3)





> ### *Weather Provider Module*:

 This module likely handles the functionality related to retrieving weather information. It encapsulates the logic for interacting with the weather API, processing the data, and providing it to a message broker (ActiveMQ). 

![image](https://github.com/Luciaafme/DACD_2aPractica/assets/145342904/0dfa08ec-90e0-4d91-89af-3afbeffbf15a)







In the control layer we can see these classes:

-  **JmsWeatherStore** class implements the **WeatherStore** interface, providing functionality to save weather predictions to a message broker (ActiveMQ) using Java Message Service (JMS).

-  **OpenWeatherMapSupplier** class is in charge of interacting with the OpenWeatherMap API to obtain weather data, this class implements the **WeatherSupplier** interface which contains methods that must be implemented in the class.

- **WeatherController** class, extending TimerTask, orchestrates the periodic retrieval and storage of weather data for predefined locations

- **Main** which is in charge of creating the necessary objects in WeatherController and deciding the frequency with which the application is executed. 


On the other hand in the model layer:

- **Weather** stores information about the weather conditions 
- **Location** represents information about the geographic location.

> ### *Accommodation Provider Module*


This module likely handles the functionality related to retrieving hotel information. It encapsulates the logic for interacting with the Xotelo API, processing the data, and providing it to a message broker (ActiveMQ). 

![image](https://github.com/Luciaafme/DACD_Final_Project/assets/145342904/2f0db0d7-8cae-40a8-b134-073161b2e5dc)



In the control layer we can see these classes:

- The **JmsAccommodationStore** class implements the **AccommodationStore** interface, providing functionality to save booking predictions to a message broker (ActiveMQ) using Java Message Service (JMS).
- The **XoteloApiSupplier** is in charge of interacting with the Xotelo API to obtain data of prices from different platforms for a determine hotel on a selected checkIn and checkOut , this class implements the **AccommodationSupplier** interface which contains methods that must be implemented in the class.
- **AccommodationController** class, extending TimerTask, orchestrates the periodic retrieval and storage of booking data.
- **Main** which is in charge of creating the necessary objects in AccommodationController and deciding the frequency with which the application is executed.

On the other hand in the model layer:

- **Booking** stores information about the booking data
- **Hotel** represents information about the hotel. 

  
> ### *Datalake Builder Module*:

This module likely deals with the subscription to the broker and constructing or managing an event store.
![image](https://github.com/Luciaafme/DACD_2aPractica/assets/145342904/978d120d-172f-4f2e-843a-506023e71cda)



Regarding the control layer we can see:

- **MapSubscriber** class listens to a specific topic on a message broker (ActiveMQ in this case) and processes incoming messages this class implements the **Subscriber** interface.
- **FileEventBuilder** class that implements the **EventStoreBuilder** interface creates and stores events in a file system based on the content of incoming messages following the structure eventstore/prediction.Weather/{ss}/{YYYYMMDD}.events.
- **Main** class is the entry point for the application.

> ### *Travel Planner Module*:
The Travel Planner module orchestrates travel planning by integrating information about hotels and weather forecasts. 

![image](https://github.com/Luciaafme/DACD_Final_Project/assets/145342904/137823ce-acf7-4fb9-982e-73a0fb904036)


In the control layer we can see these classes:

- **DatamartManager:** Responsible for creating tables, inserting data into corresponding tables, and table deletion.
- **DbConnection:** Establishes a connection with the database.
- **EventModelBuilder:** Processes each event by extracting relevant data to later insert it into the datamart.
- **MessageReceiver:** This class listens to a specific topic on a message broker (ActiveMQ in this case) and processes incoming messages. 
- **Main:** Serves as the entry point for the application.

On the other hand in the model layer:

- **Hotel:** Contains information about hotel features.
- **Weather:** Contains information about meteorological predictions.
- **Location:** Holds information about the island and the zone.

Finally on the view layer we have these classes:
- **Interface:** Creates the user interface.
- **WeatherCalculator:** Computes results related to weather to display to the user.
-  **BookingCalculator:** Computes results related to hotel reservations to display to the user.


 These classes adhere to SOLID principles; the Single Responsibility Principle is followed as each class has a specific and well-defined responsibility. The Dependency Inversion Principle is applied by using abstractions like interfaces, promoting flexibility and ease of extension. Additionally, the Open/Closed Principle is considered, allowing for potential future extensions without modifying existing code. Overall, the design encourages modularity, maintainability, and adherence to SOLID principles for effective software development.

 
