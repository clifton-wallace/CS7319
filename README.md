# CS7319 Final Project Group 11 - Brown - Pan - Wallace

# Overview
Our Collaborative Note-Taking App empowers users to create, share, and edit notes seamlessly.

Key features include:

- Secure user login and authentication.
- An dashboard to manage and access notes.
- Create, edit and delete notes.
- (Pub/Sub only) Real-time updates for shared notes, enabling effortless teamwork and collaboration.
  
# Video

 Video available here: [https://www.youtube.com/watch?v=2aWu_DLAR64](https://www.youtube.com/watch?v=MqHH50cLtQw)
 
# Compiled Code and Executables
All executables are located in the `executable` folder.
Our architecture builds upon shared components:
- The User Service and Front End share the same executable. The front end includes a toggle switch within the application to enable the Pub/Sub model.
- The Note Service uses a configuration flag to determine whether messages are published.
 -The Broadcast Service is specific to the Pub/Sub model.

**IMPORTANT** There are a number of AWS hosted components in this solution. It is recommended that you use the AWS components that are already deployed (information is in the configuration files).  All access is using shared secrets (which is not best practice) but will enable you to use those components when you run locally.

**IMPORTANT** We recommend that you run the web client locally and point to the public APIs.  It is difficult to run multiple web projects on a computer at one time. The APIs are available here:
- Notes Service: https://note-service.cs7319.com
- User Service: https://user-service.cs7319.com
- Broadcast Service: https://broadcast-service.cs7319.com

The web site is also publically available here: https://livenotes-react.vercel.app/

### User Service (API)
**Platform** 
- Java Version 21
- Maven Version 4
- Spring Boot Version 3.3.5

**Download** 
- Java - https://www.oracle.com/java/technologies/downloads/
- Spring Boot - https://start.spring.io/
- Maven - https://maven.apache.org/download.cgi
  
**Compilation**
From application directory run terminal command `mvn clean install`.

**Execution**
From application directory, after compilation run terminal command `mvn spring-boot:run`.  This will start the API application.

**IMPORTANT:** The application uses three web APIs which are all independant applications. To run local you will need to configure each one to run on a unique port. Sample command: `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`

We recommend that you use the published versions of the API which can be found here: https://user-service.cs7319.com

### Note Service
**Platform** 
- Java Version 21
- Maven Version 4
- Spring Boot Version 3.3.5

**Download** 
- Java - https://www.oracle.com/java/technologies/downloads/
- Spring Boot - https://start.spring.io/
- Maven - https://maven.apache.org/download.cgi
  
**Compilation**
From application directory run terminal command `mvn clean install`.

**Execution**
From application directory, after compilation run terminal command `mvn spring-boot:run`.  This will start the API application.

**IMPORTANT:** The application uses three web APIs which are all independant applications. To run local you will need to configure each one to run on a unique port. Sample command: `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`

We recommend that you use the published versions of the API which can be found here: https://note-service.cs7319.com

### Broadcast Service
**Platform** 
- Java Version 21
- Maven Version 4
- Spring Boot Version 3.3.5

**Download** 
- Java - https://www.oracle.com/java/technologies/downloads/
- Spring Boot - https://start.spring.io/
- Maven - https://maven.apache.org/download.cgi
  
**Compilation**
From application directory run terminal command `mvn clean install`.

**Execution**
From application directory, after compilation run terminal command `mvn spring-boot:run`.  This will start the API application.

**IMPORTANT:** The application uses three web APIs which are all independant applications. To run local you will need to configure each one to run on a unique port. Sample command: `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`

We recommend that you use the published versions of the API which can be found here: https://broadcast-service.cs7319.com

### Relational Database (User Data)
**Platform** AWS Aurora Postgres

**Download** N/A

**Compilation** N/A

**Execution**
Recommend using the existing AWS resource `postgresql://cs7319cluster-instance-1.cp8uueukswe3.us-east-1.rds.amazonaws.com:5432/CS7319`.  Connection information is in the application.properties file for the relevant services.

### Relational NoSQL Database (Note Data)
**Platform** AWS DynamoDb

**Download** N/A

**Compilation** N/A

**Execution**
Recommend using the existing AWS resource `https://dynamodb.us-east-1.amazonaws.com`.  Connection information is in the application.properties file for the relevant services.

### Notification Service
**Platform** AWS Simple Notification Service

**Download** N/A

**Compilation** N/A

**Execution**
Recommend using the existing AWS resource `arn:aws:sns:us-east-1:211125745881:note-updates`.  Connection information is in the application.properties file for the relevant services.

### Queue Service
**Platform** AWS Simple Queue Service

**Download** N/A

**Compilation** N/A

**Execution**
Recommend using the existing AWS resource `https://sqs.us-east-1.amazonaws.com/211125745881/Notes-Updates`.  Connection information is in the application.properties file for the relevant services.

### Frontend Web Site

**Platform**: Node.js >=20.0

**Download**: https://nodejs.org/en/download

    All of the following commands should be written under the assumption that you are in the root git repository.

### Installation / Development

1. Ensure Node.js and npm are installed (see above for download).
2. CD into the `frontend` directory.
3. Run `npm install` to install all required dependencies.
4. To run the development server, execute `npm run dev`.

### Compilation

The web application is built using a Node.js framework called Vite. The source is developed in React, and the output is Javascript, HTML, and CSS.

To build the final output, run the following command:

```
npm run build
```

The final build can be found in the `/dist` directory (relative to `/frontend`).

## Differences In Architectural Designs
The REST architecture follows a client-server, request-response model where clients interact with the server through HTTP endpoints for CRUD operations. It is simple, widely understood, and stateless, making it ideal for foundational tasks like user authentication and fetching notes. However, REST relies on client polling for updates, which increases latency and resource consumption, making it less suitable for real-time collaboration.

The Pub/Sub architecture, which builds on REST, introduces a message broker to decouple publishers (services sending updates) from subscribers (clients receiving updates). Using RESTful APIs for topic subscriptions and message publishing, Pub/Sub enables real-time updates by broadcasting changes to all subscribed clients immediately. This architecture scales efficiently for high-frequency updates but requires additional complexity, such as managing brokers and ensuring reliable message delivery.

For the collaborative note-taking app, the Pub/Sub architecture is the preferred choice due to its ability to handle real-time updates seamlessly. Since it extends REST with real-time capabilities, the application benefits from REST’s simplicity for non-real-time tasks like user management while leveraging Pub/Sub for efficient and scalable real-time collaboration. This hybrid approach balances ease of implementation with enhanced user experience.

## Proposal Changes

Our initial proposal included a comparison between a microservices and monolith architecture. Because monolith wasn't covered in class, we opted to switch to a pub/sub approach, which builds upon the REST/microservices architecture we originally discussed. Due to its fast speed and concurrent nature, we believe that adding a pub/sub architecture on top of our existing architecture is a beneficial decision. On top of providing ease-of-use, pub/sub gives us a unique opportunity to discuss very similar, yet distinctly different architectures in one development cycle.
