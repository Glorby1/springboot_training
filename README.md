# springboot_training

Here is how to use it : 

git clone into a repo
for mySQL :
  mysql -u <username> -p 
  <password>
  CREATE DATABASE taskManager;
  USE taskManager; (if you want to check the db)
  exit

  -> Don't forget to put username and password in the application.properties

in package com.example.demo, dataInitializer is here to create an ADMIN for the tests. Removable later on. username : admin, password : admin123

Now, on postman, curl, or whatever you use for HTTP request : 


1. CONNEXION ADMIN :
   http://localhost:8080/auth/login   (no auth, POST)
     {
        "username": "admin",
        "password": "admin123"
      }
  -> take the bearer for further steps.

2. POST USER (do both, for further tests, for authorization):
   http://localhost:8080/users (post, admin bearer token)
   {
      "username": "john",
      "password": "pass123",
      "role": "SUPERVISOR"  
   }
   {
      "username": "trung",
      "password": "pass345",
      "role": "USER"  
   }
   
4. GET USERS
   http://localhost:8080/users
   http://localhost:8080/users/{id_user}
   
6. PUT USERS
   http://localhost:8080/users/{id_user}
   
8. DELETE USERS 
   http://localhost:8080/users/{id_user} #Deleting a user will automatically delete the tasks he has from the db

8. POST TASKS
   http://localhost:8080/tasks
   {
      "title": "Kill the database",
      "description": "do a gitpush --force on the group project",
      "assignedToUserId": 3
   }
9. GET
   http://localhost:8080/tasks
   http://localhost:8080/tasks/{id_task}
   http://localhost:8080/tasks/user/{user_id} #did not do a /me because this one can be used for users (to see their own tasks on frontend) with adding a bit of security, but /me is maybe better because easier

10. PUT :
   http://localhost:8080/tasks/{id_task}
   http://localhost:8080/tasks/{id_task}/done #change the boolean for a task, true = done, false = not done, really basic
   http://localhost:8080/tasks/{id_task}/notdone

11. DELETE :
   http://localhost:8080/tasks/{id_task}
    

When this is done, you can try to log with a USER/SUPERVISOR account and try again these requests, for authorizations. 
   


Versionning and dependencies : 


SpringBoot 3.5
Maven 4.0.0
Spring boot security
Spring boot web 
Spring boot dev tools (reboot on each modification, a bit like angular)
Lombok (all constructors, getter setter)
JJWT










   

  
