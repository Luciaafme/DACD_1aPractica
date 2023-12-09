# Weather Forecast

Lucía Afonso Medina

University of Las Palmas de Gran Canaria

2nd year in Engineering and Data science 

Subject: Data science application development

School of computer engineering

# Functionality


In this project two modules have been developed. The provider module is designed to periodically fetch meteorological data from the OpenWeather API for the 8 islands over the next 5 days, precisely at 12 p.m. daily, with a frequency of every 6 hours. Subsequently, the module transmits this event to the "prediction.Weather" topic.

On the other hand, the Event Store Builder module is responsible for the organized temporal storage of events obtained from the broker. It subscribes to the relevant topic, serializing events in the eventstore directory following a specific structure: "eventstore/prediction.Weather/{ss}/{YYYYMMDD}.events." Here, YYYYMMDD represents the year, month, and day derived from the event's timestamp, while ".events" serves as the file extension for storing events associated with a specific day.

API --> https://openweathermap.org/forecast5


# How to run the program

This process involves downloading, extracting, and running two modules - event-store-builder and weather-provider. The first module is responsible for creating the "eventstore" directory at a specified location, and the second module requires your API key as a         parameter for execution.

You should follow these steps: 
1. Download the ZIP files from the release.
2. Unzip the contents of each ZIP file to the location of your choice.
3. Run the event-store-builder module:
   >Execute the event-store-builder module from its location within your system. Use the provided image as a reference, passing the desired path as a parameter to create the "eventstore" directory. As an example, we want to create this directory on the Desktop.


![image](https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/ec58170d-1440-491e-a4bf-e765b5055789)
 

4. Next, run the weather-provider module:
   >Execute the weather-provider module from its location within your system, following the same approach as in the previous step. This time, pass your API key as a parameter.


![image](https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/b7251a5a-1f2d-4ef1-8c10-c788098fcb64)



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

![image](https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/d744d944-dd4d-40b8-8906-86154a68b5b4)



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


![image](https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/66f56817-c003-4bd8-8f0e-cfcb6e54aa3b)


Regarding the control layer we can see:

- **MapSubscriber** class listens to a specific topic on a message broker (ActiveMQ in this case) and processes incoming messages this class implements the **Subscriber** interface.
- **FileEventBuilder** class builds and stores events to a file system based on the content of incoming messages implements the **EventStoreBuilder**.
- **Main** class is the entry point for the application.



 These classes adhere to SOLID principles; the Single Responsibility Principle is followed as each class has a specific and well-defined responsibility. The Dependency Inversion Principle is applied by using abstractions like interfaces, promoting flexibility and ease of extension. Additionally, the Open/Closed Principle is considered, allowing for potential future extensions without modifying existing code. Overall, the design encourages modularity, maintainability, and adherence to SOLID principles for effective software development.

