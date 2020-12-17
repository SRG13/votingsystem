# Topjava graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

## The task is:
Build a voting systen for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

## API documentation and curl commands:

**Usage:**

The app is designed to vote which menu of the day users want to try. Users can vote for the menu of the day once a day, or change their mind before 11:00. 
Admin can add a menu for the restaurant once a day.


**Entities:**

User - for storing system users<br/>
Role - for storing roles of user<br/>
Restaurant - storing restaurants<br/>
Menu - for storing menus, have list of dishes and reference on restaurant<br/>
Dish - for storing dishes, have reference on menu<br/>
Vote - for storing votes, have reference on user and menu<br/>


**Users:**
| login | password | role |
|---|---|---|
| admin@yandex.ru | admin | ADMIN |
| user@gmail.com | pass123 | USER |
| Bar@gmail.com | s3cr3t | USER |
