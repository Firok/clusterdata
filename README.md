# Clustered Data Warehouse

Spring boot Embedded Tomcat Spring MVC Spring Data JPA Hibernate Thymeleaf MySQL Docker

## Set-up

This project requires Java 8 and above, You can use Maven Command to build the project

## How to run the project

    1. Create MySQL Database "progressdb" and after running the project all the table will be created automatically
    2. Load it up on Eclipse/Spring Tool Suite/Intellij IDEA
    3. Do Maven "clean instal"
    3. Do Maven "test" for unit test
    4. Create Temporary Folder for storing all the uploaded file as archive, but optional as the project will run without the archive    file You can change folder path at `package com.progresssoft.app.util.Config ATT_HOT_FOLDER_BASE_PATH`
    5. Embedded Tomcat, No Need to deploy WAR File
    6. Run project as Spring Boot App
    7. Open a browser and navigate to http://localhost:8080/clusterdata
    8. Now you see the web interface to upload file and inquire about the result

## How to run with docker

    1. mvn clean package docker:buld
	 2. Navigate to Docker folder at the target folder
	 3. Inside the Docker folder will be ClusterData.jar and Dockerfile  which the plugin maven has built
	 4. Go to the terminal 
	 5. type 'docker images' to see the created docker image 'ClusterData'
	 6. type 'docker ps' to see if any container is running' 
	 7 type 'docker run -it -p <map-port-number>:8080 ClusterData' to map local machine port number
	 8 Open a browser and navigate to http://localhost:<map-port-number>/clusterdata