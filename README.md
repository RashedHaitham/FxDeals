The FX Deal Data Warehouse project aims to develop a data warehouse for Bloomberg, allowing analysis of Foreign Exchange (FX) deals. Here are the instructions for running the project:

## Running on localhost (without Docker)
1. Execute `mvn clean install` to clean existing files and install packages.
2. Start the Spring Boot application using `mvn spring-boot:run`.
3. Ensure that you update the properties file to match your local database configuration (datasource url).

## Running with Docker
1. Execute `mvn clean install` to clean existing files and install packages.
2. Navigate to the directory containing your Docker Compose file (`docker-compose.yml`).
3. Open a terminal or command prompt in that directory.
4. Run `docker-compose up` to start the services defined in your Docker Compose file.
5. To stop the running containers, use `docker-compose down`.

### Docker Compose File:
Defines two services: `mysqldb` and `fxdeal`.

#### mysqldb Service:
- Uses the official MySQL Docker image.
- Defines environment variables for MySQL root password and database name.
- Maps port 3306 on the host to port 3306 on the container.
- Includes a health check to ensure the MySQL service is healthy.

#### fxdeal Service:
- Builds the Docker image from the current directory (`.`).
- Restarts the container always.
- Maps port 3003 on the host to port 3003 on the container.
- Depends on the `mysqldb` service and waits until it's healthy before starting.

### Guide to Build and Run the Project
1. Import the project and ensure you have the correct JDK (version 21 or higher) set.
2. Configure Maven 3+ in your system (or download it from [here](https://maven.apache.org/download.cgi)).
3. Right-click on the project, go to **Run as**, and select **Maven clean** (you can also run `mvn clean` directly in the command prompt).
4. Right-click on the project, find **Maven**, and click on **Update project**. Check the **Update snapshots forcefully** option and press OK.
5. Refresh your project and run a clean build.

### Access Endpoints Using Swagger
- `localhost:3003/api/deals` for GET requests.
- `localhost:3003/api/deals/deal` for POST requests.