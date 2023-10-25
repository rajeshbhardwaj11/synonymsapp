# synonymsapp
synonyms app

Steps to run the project locally.

1. Create the database synonyms_db

2. Add 2 users in UserInfo table using endpoints on postman
    Method POST
    http://localhost:8080/auth/addNewUser
    Requestbody1
    {
        "name": "sunil",
        "email": "sunil@email.com",
        "password": "123",
        "roles": "ROLE_ADMIN"
    }

    Requestbody2
    {
        "name": "rajesh",
        "email": "rajesh@email.com",
        "password": "456",
        "roles": "ROLE_USER"
    }
  
  
3.  Generate the token by api http://localhost:8080/auth/generateToken
    Method POST
    RequestBody:
    {
        "username": "sunil",
        "password": "123"
    }

4. Add synonyms in the database. Only Admin can add the synonyms
   API: http://localhost:8080/synonyms/add
   Method POST
   RequestBody:
   {
    "key": "Beautiful",
    "values": ["Gorgeous","Dazzling"]
   }
   Also we need to pass the Admin token under Header like Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWplc2giLCJpYXQiOjE2OTgyMzM2NzcsImV4cCI6MTY5ODIzNTQ3N30.PK4LNuCT98fLSajtQbcYcvaF3NYCDEY6gciERujgIZU

5. We can update the synonyms. This operation can be performed by admin.
   API: http://localhost:8080/synonyms/update
   Method: PUT
   RequestBody:
   {
    "key": "Beautiful",
    "values": ["Splendid","Magnificent"]
   }
   we need to pass the Admin token under Header section like Authorization:

6. We can get the synonyms by word using api: http://localhost:8080/synonyms/get/Beautiful
   Method GET. Here Beautiful is the word which need to pass to get list of corresponding synonyms. This action can we done by USER and ADMIN and we need to pass token to get the response

7. API to delete the record from DB: http://localhost:8080/synonyms/delete/Beautiful
   Only Admin can delete the records from db 

