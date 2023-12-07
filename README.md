# Weather Forecast

Lucía Afonso Medina

University of Las Palmas de Gran Canaria

2nd year in Engineering and Data science 

Subject: Data science application development

School of computer engineering

# Functionality

The objective of this internship is to develop a Java application that performs periodic queries, every 6 hours, to the API of a weather service to obtain the weather forecast for the 8 islands for the next 5 days at 12 p.m. each day. The acquired information must be stored in a SQLite database, where there will be a dedicated table for each island and an entry for each day. In these tables will be stored, for each day, the meteorological data, including temperature, precipitation probability, humidity, clouds and wind speed.

API --> https://openweathermap.org/forecast5


# How to run the program

To make the program work you must enter the apikey and the name of the database as main parameters as specified in the images below.

First step: 

<img width="492" alt="image" src="https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/08257ec3-d914-41ca-b94d-a628151d321b">

Second step:

<img width="529" alt="image" src="https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/2de98c67-2eb1-4668-a47f-76a6f1890009">


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

Finally, for documentation, Markdown, a plain text formatting syntax that facilitates the creation of readable and well-structured documents, was used. Markdown is widely used for writing technical documentation and READMEs in GitHub repositories, due to its simplicity and readability.




    
# Design

The application design follows the principles of object-oriented design and utilizes the Model-View-Controller (MVC) architectural pattern to separate concerns and maintain modular and easily maintainable code. 

In this particular application, the decision to create two modules, "weather-provider" and "event-store-builder," suggests a modular and component-based approach. Let's break down the reasons for creating these modules:


> ### *Weather Provider Module*:

 This module likely handles the functionality related to retrieving weather information. It encapsulates the logic for interacting with the weather API, processing the data, and providing it to a message broker (ActiveMQ). 

(Insert class diagram Weather-Provider)

In the control layer we can see these classes:

-  **JmsWeatherStore** class implements the **WeatherStore** interface, providing functionality to save weather predictions to a message broker (ActiveMQ) using Java Message Service (JMS).

-  **OpenWeatherMapSupplier** class is in charge of interacting with the OpenWeatherMap API to obtain weather data, this class implements the **WeatherSupplier** interface which contains methods that must be implemented in the class.

- **WeatherController** class, extending TimerTask, orchestrates the periodic retrieval and storage of weather data for predefined locations

- **Main** which is in charge of creating the necessary objects in Weathercontroler and deciding the frequency with which the application is executed. 


On the other hand in the model layer:

- **Weather** stores information about the weather conditions 
- **Location** represents information about the geographic location.

  
> ### *Event Store Builder Module*:

This module likely deals with the subscription to the broker and constructing or managing an event store.

(Insert class diagram Event -  Store -Builder)

Regarding the control layer we can see:

- **MapSubscriber** class listens to a specific topic on a message broker (ActiveMQ in this case) and processes incoming messages this class implements the **Subscriber** interface.
- **FileEventBuilder** class builds and stores events to a file system based on the content of incoming messages implements the **EventStoreBuilder**.
- **Main** class is the entry point for the application.



 These classes adhere to SOLID principles; the Single Responsibility Principle is followed as each class has a specific and well-defined responsibility. The Dependency Inversion Principle is applied by using abstractions like interfaces, promoting flexibility and ease of extension. Additionally, the Open/Closed Principle is considered, allowing for potential future extensions without modifying existing code. Overall, the design encourages modularity, maintainability, and adherence to SOLID principles for effective software development.

