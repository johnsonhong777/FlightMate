# FLIGHTMATE

FlightMate is an application dedicated for pilots and airport administrators to maintain a log of flight hours, as well as preparing flight plans and schedules for aircrafts at an airport. Administrators can approve/deny pilot flight hours, add aircrafts, schedule maintenance and create a booking system for aircrafts.

## Folder & File Structure
The project is set up as a Java Web Project. The structure looks as follows:

```
-src
|- main
	|- java
		|- com.flightmate
			|- beans
			|- dao
			|- libs
			|- builders
			|- services
			|- servlets
	|- webapp
		|- components
		|- css
		|- META-INF
		|- WEB-INF
			|- lib
```

- JSP files are stored within the `webapp` folder.
- the `/WEB-INF/lib` folder contains the `mysql-connector-j` and `taglibs` JAR files.
- the `/css` folder contains stylings for the site
- the `/components` folder contains reusable components which are included across multiple pages in the application. These take advantage of the `include` feature from JSP to add them dynamically in the web app.

In the `java` folder, the main package is served under `com.flightmate`.

- The `/beans` folder contains the different classes used in our application
- The `/dao` folder contains files related to the database, such as connection (in `DBConnection`), table creation (in `ApplicationDao`) and individual queries and mutations (such as `UserDao`)
- The `/libs` folder will contain `Enums`, `Abstract` classes and `Interfaces` extended / implemented by other classes throughout the application. Serves as an easy reference.
- The `/libs/builders` folder contains Builder functions, mostly used in the specific `dao` files.
- The `/services` folder will handle application-wide services, such as the `Session`.
- Finally, the `/servlets` folder will contain the servlets for each page for the website, along with the necessary controllers and logic for the webpage being accessed by the user.


## To run this project
This project was created in Java using JSP and JSTL. All libraries are included within the project itself. Additionally, the database and tables will automatically be created on your behalf when ran for the first time.

To run successfully, you must have the following:

- MySQL 8 or later installed on your system. You can install MySQL 8 at the following link: https://dev.mysql.com/downloads/mysql/8.0.html
- The MySQL instance must be running. If using Windows, you can enable the instance by opening your `services.msc` file, find `MySQL80` (or whichever version you're running), and make sure the service has started:
- Next, ensure you have Apache Tomcat 9 installed on your system. This can be done through your IDE such as Eclipse. This project is optimized for Apache Tomcat 9.0.
- Import the .WAR file provided in the submission, or follow the instructions below to import the project using Git.
- Once imported, right-click on the directory in your IDE, and select "Run on Server". The web server should be hosted at the following link: http://localhost:8080/flightmate/

Happy exploration!

## To contribute

1. You must create a Pull Request (also known as a PR). Naming conventions usually work as <your initials>/<feature-name>.
2. Code must be approved by one other team member. It keeps everyone accountable, and makes sure we push quality code.

## To start working on this code

### If you're using Eclipse

1. Select File > Import
![image](https://github.com/user-attachments/assets/5cfd6d26-8614-48ca-8f1d-ce1885c08049)

2. Select Git > Projects from Git
![image](https://github.com/user-attachments/assets/1cdf68ff-439a-4d3d-bc40-9ba124ae3233)

3. Select Clone URI
![image](https://github.com/user-attachments/assets/fcf5d1de-dd30-41e2-a7f0-f504ef4a49fc)

4. In URI, paste this: `https://github.com/GabrielMontplaisir/FlightMate.git`
![image](https://github.com/user-attachments/assets/a7189aa4-4f3f-45b9-968e-07cf60ce891b)

You're going to have to create a token for authentication. You can retrieve this from Github: [https://github.com/settings/tokens](https://github.com/settings/tokens)
- Select `Personal Access Tokens (Classic)`, then click `Generate New Token`
- Set an expiry date if you want, or never. Up to you. Select the options below only.

![image](https://github.com/user-attachments/assets/8ff8fa10-a7ac-4b38-a86f-bbfc56422a2e)

You will then obtain a code. This is you "password" for Eclipse. Paste your github email address in the "User" section, and your token in the "password" section.

Click "next", until you finish. This will import the project.


Feel free to ask questions on MS teams if you need more help!
