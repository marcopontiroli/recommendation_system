# Recommendation System Application

This is a Spring Boot application for the ContentWise challenge
## Requirements

Make sure you have the following installed on your system:
- Docker: [Docker](https://www.docker.com/)

## Getting Started

### Clone the Repository
Clone this repository to your local machine:

git clone <repository-url>
cd <repository-name>


### Build and Run the Application
Build and start the application along with the MySQL database:

docker-compose up --build


### Access the Application
- **Spring Boot Application**: Available at `http://localhost:8080`
- **MySQL Database**:
  - Host: `localhost`
  - Port: `3307`
  - Username: `admin`
  - Password: `admin`
  - Database: `recommendation_db`

### Check Application Health
You can verify that the application is running by accessing:

http://localhost:8080/actuator/health

### Swagger
You can read APIs descriptions and send requests with swagger by accessing:

http://localhost:8080/swagger-ui/index.html

### ER Diagram

You can view the ER diagragm for the application's datamodel by opening files ER.png or er.mdj (StarUML)

## Stopping the Application
To stop and remove all containers, run:

docker-compose down


## Notes
- If the default ports `8080` or `3307` are in use, modify the `docker-compose.yml` file to specify different ports.
- Ensure that Docker is running before starting the application.

For any issues, please contact me at pontiroli.marco@gmail.com
