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

SqLite

        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.34.0</version>
        </dependency>

Finally, for documentation, Markdown, a plain text formatting syntax that facilitates the creation of readable and well-structured documents, was used. Markdown is widely used for writing technical documentation and READMEs in GitHub repositories, due to its simplicity and readability.




    
# Design

Class Diagram

<img width="582" alt="image" src="https://github.com/Luciaafme/DACD_1aPractica/assets/145342904/5b88e1ff-79e3-4ee1-baeb-161853c542e1">




First of all, two POJO classes have been created, Weather and Location, in order to extract the information from the JSON provided by the API and set the values of each attribute of the variables. Both classes belong to the model layer, Weather stores information about the weather conditions and Location represents information about the geographic location. Also in the model layer we can see to interfaces WeatherSupplier and WeatherStore

Regarding the control layer, we have the OpenWeatherMapSupplier class which is in charge of interacting with the OpenWeatherMap API to obtain weather data, this class implements the WeatherSupplier interface which contains methods that must be implemented in the class. SQLiteWeatherStore is use to interact with a SQLite database and manage the storage of weather data, this class also implements an interface called WeatherSupplier. Furthermore, the Weather Controller class is responsible for coordinating the weather data requests through OpenWeatherMapSupplier and storing them in the database through SQLiteWeatherStore. Finally the Main which is in charge of creating the necessary objects in Weathercontroler and deciding the frequency with which the application is executed. The control layer is responsible for acting as an intermediary between the model layer and the view layer, which in this program has not been required as there is no user interface.


This design adheres to the SOLID principles, promoting scalability and maintenance of the system over time. The clear separation of responsibilities between the model, view, and controller layers enhances code organization and facilitates the extension and modification of the system. The use of interfaces WeatherSupplier and WeatherStore adheres to SOLID's Dependency Inversion Principle, allowing for flexibility and easy substitution of components. Additionally, the application of the Single Responsibility Principle (SRP) is evident in the design through the distinct responsibilities assigned to each class within both the model and control layers.
