<a name="readme-top"></a>
<br />
<div align="center">
  <a href="public/SMT_UTN.png">
    <img src="https://i.imgur.com/rY92D2Q.png" alt="Logo of San Miguel de Tucumán and National Technological University">
  </a>
<h3 align="center">Smart Collection for a Clean Tucumán (API)</h3>
<h4 align="center">Fleet control system for urban solid waste collection trucks in San Miguel de Tucumán</h4>
</div>

# About the project:
This project proposes the implementation of a real-time GPS tracking system to control the operations of garbage collection trucks in the city. Designed to complement the GIRSU plan, a resource optimization strategy to ensure innovative and efficient management of urban solid waste in San Miguel de Tucumán.

We aim not only to improve efficiency and control in waste collection but also to contribute to reducing operational costs and environmental impact, aligning with the city's sustainability objectives. By keeping citizens informed about collection schedules and routes, we encourage greater responsibility and community participation in waste management.

# Objectives
- **Integration with GIRSU:** Provide a technological tool that strengthens and complements current initiatives of the GIRSU program for a cleaner, more efficient city.
- **Improvement of public service:** Enhance the quality and efficiency of waste collection service, offering a more agile response to operational challenges.
- **Generation of strategic data:** Provide a detailed history of collection operations for informed decision-making and future planning.
- **Citizen participation:** Promote environmental awareness and active participation of citizens in waste management through transparency and accessibility of information.

# Project scope
- **Implementation of GPS Devices:** Installation of GPS tracking devices on urban solid waste collection trucks.
- **Centralization of Data:** Transmission of real-time location information to a centralized server, with secure and accessible storage.
- **Public Access to Information:** Development of an interactive web platform that shows the real-time location of collection trucks and other relevant data.
- **Administrative Management:** Creation of a control panel for managing users, trucks, and collection routes, as well as accessing a detailed history of routes, speeds, driver data, stops, delays, and other operational aspects.

# General scheme
<img src="https://i.imgur.com/yFy4IQc.png" alt="Logo of San Miguel de Tucumán and National Technological University">

# Technologies used in this API:
![Java]( 	https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

![Vim](https://img.shields.io/badge/VIM-%2311AB00.svg?&style=for-the-badge&logo=vim&logoColor=white)

![WebStorm](https://img.shields.io/badge/webstorm-143?style=for-the-badge&logo=webstorm&logoColor=white&color=black) ![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

![love](http://ForTheBadge.com/images/badges/built-with-love.svg)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Features

- [x] Real-time update of truck locations via WebSocket
- [x] CRUD operations for trucks
- [ ] CRUD operations for users
- [ ] User authentication and authorization
- [ ] Alerts and notifications for truck status

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
# Getting Started

To obtain a working local copy, follow these simple steps.

## Prerequisites
* Java 21

# Installation

1. Clone the repo
   ```sh
   git clone https://github.com/ramos-adrian/girsu-gps-api
   ```
   
2. Create a .env file in the root directory with the following variables:
   ```sh
    #.env
    ADMIN_USERNAME=admin
    ADMIN_PASSWORD=secret
   ```
   
4. Run the development server
   ```sh
   ./mvnw spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Contact
Ramos, Adrián David - adrian@ramos.ar

[![linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](www.linkedin.com/in/adrian-david-ramos) 