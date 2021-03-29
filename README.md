# URLShortenerProject: Assignment to Shorten the length of URL's

In this assignment, I will explain how my project will run but before that I assume that Docker, H2 Database and Netbeans are installed in the system.

1) Download the Code from the GitHub and import the same in your respective IDE.

2) Run the UrlProjectApplication inside the com.url.shortner package which will initiate our Spring Container and start our tomcat server on port 8080.

3) Now, from the output screen copy the connection url to establish a connection with our database (H2 Database).Example: jdbc:h2:mem:a6f820b6-0fc0-4872-8646-81a184136a82.

4) Make sure you have installed H2 as when you copy the JDBC url after that go to your browser and enter localhost:8080/h2-console.
 
5) Paste your connection url under JDBC url and click Test Connection once it says Test successful then click Connect.
 
6) After this, open Postman where we will initiate POST and GET API calls and receive our shortlink.

7) Firstly, In the POST calls enter localhost:8080/generate in the cell and within its body (set to JSON) enter the long url and expiration date(optional) and click Send.

8) The ShortLink along with Original URL and its expiration date will appear in the Response body and the same can be seen in our H2 database by writing Select * from URL and enter Run.
 
9) For the GET API (change POST to GET) call write localhost:8080/{shortlink} in the cell and we can see the web page in our Response body in Preview Section.

10) After that we build our project by right click on our project and then go to Run Maven -> Goals, a following window appears. (as per Netbeans)
 
11) In the Goals section write mvn clean install to build and get our target folder which will be used to make our dockerfile.

12) The following commands were used in the command prompt(docker installed) to build the docker file.

            •	Go to the respective folder/directory where the project is kept and run docker build . command.

            •	Get the Image ID using docker image ls. Then, run the command docker run -p 8081:8080 -d {imageID} to get our running container at port 8081.

            •	Then you can generate the POST request by writing localhost:8081/generate in Postman to get the short link and writing the same to the browser as localhost:8081/{shortlink}.

