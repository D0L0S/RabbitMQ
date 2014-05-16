# RabbitMQ Controller v2.0

-----------------------------
CONTENTS OF THIS FILE
-----------------------------

* Introduction
* Requirements
* Installation
* Configuration

------------------------------
INTRODUCTION
------------------------------

Rabbit Controller is a simple command line util that enables the user to shedule tests over the RabbitMQ bus. There is currently functionality for randomisation if duration as well as set level duration. 
Threading is in place for the Reciever however not currently for the Publisher.

------------------------------
REQUIREMENTS
------------------------------

* RabbitMQ
* Jackson 

------------------------------
INSTALLATION
------------------------------

In order to install Rabbit Controller simply pull the main repository. Once this has been done ensure that all files have correct privilages. Lazy Sollution: chmod 775 *.*

------------------------------
CONFIGURATION
------------------------------

Configuration is controlled from within the Rabit.properties file. This sets the RabbitMQ server that will be connected to as well as the login details.
