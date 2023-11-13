# Weather Forecast

Lucía Afonso Medina

University of Las Palmas de Gran Canaria

2º year in Data science application development

School of computer engineering

# Functionality

The objective of this internship is to develop a Java application that performs periodic queries, every 6 hours, to the API of a weather service to obtain the weather forecast for the 8 islands for the next 5 days at 12 p.m. each day. The acquired information must be stored in a SQLite database, where there will be a dedicated table for each island and an entry for each day. In these tables will be stored, for each day, the meteorological data, including temperature, precipitation probability, humidity, clouds and wind speed.

API --> https://openweathermap.org/forecast5

(INCLUDE NEEDS REQUIERED TO USE THE PROGRAM)

# Resources used
The development environment used was IntelliJ IDEA, a widely used and highly integrated IDE. This IDE is connected to various tools and technologies to facilitate software development. One of the version control systems used was Git, which allows detailed tracking of changes in the source code. In addition, GitHub was used as a cloud hosting service to store the different Git repositories.

For dependency management and project build, Maven, a build automation tool that simplifies the process of compiling, testing and managing project dependencies, was used.

For documentation, Markdown, a plain text formatting syntax that facilitates the creation of readable and well-structured documents, was used. Markdown is widely used for writing technical documentation and READMEs in GitHub repositories, due to its simplicity and readability.

# Design
First of all, two POJO classes have been created, Weather and Location, in order to extract the information from the JSON provided by the API and set the values of each attribute of the variables. Both classes belong to the model layer, Weather stores information about the weather conditions and Location represents information about the geographic location.

(INCLUDE CLASS DIAGRAM)

Regarding the control layer, we have the OpenWeatherMapSupplier classes which is in charge of interacting with the OpenWeatherMap API to obtain weather data, this class implements the WeatherSupplier interface which contains methods that must be implemented in the class. SQLiteWeatherStore used to interact with a SQLite database and manage the storage of weather data, this class also implements an interface called WeatherSupplier. There is also a FileManager class which is responsible for extracting data passed through text files such as in this case the apikey (stored in apikey.txt) and the connection to the database (stored in path.txt). Finally, the Weather Controller class is responsible for coordinating the weather data requests through OpenWeatherMapSupplier and storing them in the database through SQLiteWeatherStore and the Main which is in charge of creating the necessary objects in Weathercontroler and deciding the frequency with which the application is executed. The control layer is responsible for acting as an intermediary between the model layer and the view layer, which in this program has not been required as there is no user interface.

This design facilitates scalability and maintenance of the system as it evolves over time. The clear separation of responsibilities between model, view and controller layers improves code organisation and allows for easy extension and modification of the system.
