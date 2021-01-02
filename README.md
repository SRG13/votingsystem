# Topjava graduation project [![Codacy Badge](https://app.codacy.com/project/badge/Grade/1cf6dbce7522443e89c6bc7535675916)](https://www.codacy.com/gh/SRG13/votingsystem/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SRG13/votingsystem&amp;utm_campaign=Badge_Grade)
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

**Transfer Objects:**

RestaurantTo - for storing menu of current day

**Users:**
| login | password | role |
|---|---|---|
| admin@yandex.ru | admin | ADMIN |
| user@gmail.com | pass123 | USER |
| bar@gmail.com | s3cr3t | USER |

### Restaurant cURL examples: ###
---
**get all restaurants**

`curl -L -X GET 'http://localhost:8080/restaurants'`

**Response:**

```
[
    {
        "id": 100003,
        "name": "107 West",
        "description": "Traditional American food",
        "address": "2787 Broadway Ave, New York"
    },
    {
        "id": 100004,
        "name": "11B EXPRESS",
        "description": "Pizza, Sushi, Cheeze",
        "address": "174 Ave B, New York"
    },
    {
        "id": 100005,
        "name": "Cesario",
        "description": "Italian food",
        "address": "601 Sutter St, San Francisco"
    }
]
```

---

**get restaurant by id**

`curl -L -X GET 'http://localhost:8080/restaurants/100003'`

**Response:**

```
{
    "id": 100003,
    "name": "107 West",
    "description": "Traditional American food",
    "address": "2787 Broadway Ave, New York",
    "menu_of_day": {
        "id": 100013,
        "name": "Burrito Dinner",
        "date": "2020-12-31",
        "dishes": null
    }
}
```

---

**create restaurant**

`curl -L -X POST 'http://localhost:8080/restaurants' -H 'Content-Type: application/json' --user 'admin@yandex.ru:admin' --data-raw '{"name": "Created Name","description": "Created Description","address": "Created Address"}'`

**Response:**

```
{
    "id": 100058,
    "name": "Created Name",
    "description": "Created Description",
    "address": "Created Address"
}
```

---

**update restaurant**

`curl -L -X PUT 'http://localhost:8080/restaurants/100003' -H 'Content-Type: application/json' --user 'admin@yandex.ru:admin' --data-raw '{"id": 100003,"name": "Updated Name","description": "Updated Description","address": "Updated Address"}'`

---

**delete restaurant**

`curl -L -X DELETE 'http://localhost:8080/restaurants/100003' --user 'admin@yandex.ru:admin'`

---

### Menu cURL examples: ###
---
**get all menus of restaurant**

`curl -L -X GET 'http://localhost:8080/restaurants/100005/menus'`

**Response:**

```
[
    {
        "id": 100008,
        "name": "Special Menu",
        "date": "2020-09-05",
        "dishes": null
    },
    {
        "id": 100010,
        "name": "Night Pastas",
        "date": "2020-11-07",
        "dishes": null
    },
    {
        "id": 100012,
        "name": "Appetizers Sale",
        "date": "2020-11-11",
        "dishes": null
    }
]
```

---

**get menu by id**

`curl -L -X GET 'http://localhost:8080/restaurants/100005/menus/100012'`

**Response:**

```
{
    "id": 100012,
    "name": "Appetizers Sale",
    "date": "2020-11-11",
    "dishes": [
        {
            "id": 100036,
            "name": "Crab Cakes",
            "price": 14.00
        },
        {
            "id": 100037,
            "name": "Caprese",
            "price": 11.00
        },
        {
            "id": 100038,
            "name": "House Salad",
            "price": 12.00
        }
    ]
}
```

---

**create menu**

`curl -L -X POST 'http://localhost:8080/restaurants/100005/menus' -H 'Content-Type: application/json' --user 'admin@yandex.ru:admin' --data-raw '{"name": "New Name","dishes": [{"name": "New Dish 1","price": "10.0"},{"name": "New Dish 2","price": "12.03"},{"name": "New Dish 3","price": "9.40"}]}'`

**Response:**

```
{
    "id": 100063,
    "name": "New Name",
    "date": "2021-01-01",
    "dishes": [
        {
            "id": 100064,
            "name": "New Dish 1",
            "price": 10.0
        },
        {
            "id": 100065,
            "name": "New Dish 2",
            "price": 12.03
        },
        {
            "id": 100066,
            "name": "New Dish 3",
            "price": 9.40
        }
    ]
}
```

---

**delete menu**

``curl -L -X DELETE 'http://localhost:8080/restaurants/100005/menus/100012' --user 'admin@yandex.ru:admin'``

---

**vote for menu**

`curl -L -X POST 'http://localhost:8080/restaurants/100005/menus/100013' --user 'bar@gmail.com:s3cr3t'`

**Response:**

```
{
    "id": 100058,
    "vote_date_time": "2021-01-01T00:22:39"
}
```

---

### Profile cURL examples: ###
---

**get authorized user**

`curl -L -X GET 'http://localhost:8080/profile' --user 'bar@gmail.com:s3cr3t'`

**Response:**

```
{
    "id": 100002,
    "name": "Anna",
    "email": "bar@gmail.com",
    "registered": "2020-02-19T09:13:42",
    "enabled": true,
    "roles": [
        "USER"
    ]
}
```

---

**register user**

`curl -L -X POST 'http://localhost:8080/profile/register' -H 'Content-Type: application/json' --data-raw '{"name": "New User","email": "newemail@gmail.com","password": "newPassword"}'`

**Response:**

```
{
    "id": 100059,
    "name": "New User",
    "email": "newemail@gmail.com",
    "registered": "2021-01-01T00:38:11",
    "enabled": true,
    "roles": [
        "USER"
    ]
}
```

---

**update authorized user**

`curl -L -X PUT 'http://localhost:8080/profile' -H 'Content-Type: application/json' --user 'bar@gmail.com:s3cr3t' --data-raw '{"id": 100002,"name": "Updated Name","email": "updatedemail@gmail.com","password": "updatedpass"}'`

---

**delete authorized user**

`curl -L -X DELETE 'http://localhost:8080/profile' --user 'bar@gmail.com:s3cr3t'`

---

**get votes of authorized user**

`curl -L -X GET 'http://localhost:8080/profile/votes' --user 'bar@gmail.com:s3cr3t'`

**Response:**

```
[
    {
        "id": 100055,
        "vote_date_time": "2020-11-11T01:00:03"
    },
    {
        "id": 100052,
        "vote_date_time": "2020-11-07T18:13:55"
    },
    {
        "id": 100050,
        "vote_date_time": "2020-09-05T21:45:00"
    }
]
```

---

## Admin cURL examples: ##
---

**get all users**

`curl -L -X GET 'http://localhost:8080/admin/users' --user 'admin@yandex.ru:admin'`

**Response:**

```
[
    {
        "id": 100000,
        "name": "Petr",
        "email": "admin@yandex.ru",
        "registered": "2020-01-30T12:00:44",
        "enabled": true,
        "roles": [
            "ADMIN",
            "USER"
        ]
    },
    {
        "id": 100001,
        "name": "Sergey",
        "email": "user@gmail.com",
        "registered": "2020-02-05T13:05:11",
        "enabled": true,
        "roles": [
            "USER"
        ]
    },
    {
        "id": 100002,
        "name": "Anna",
        "email": "bar@gmail.com",
        "registered": "2020-02-19T09:13:42",
        "enabled": true,
        "roles": [
            "USER"
        ]
    }
]
```

---

**get user by id**

`curl -L -X GET 'http://localhost:8080/admin/users/100001' --user 'admin@yandex.ru:admin'`

**Response:**

```
{
    "id": 100001,
    "name": "Sergey",
    "email": "user@gmail.com",
    "registered": "2020-02-05T13:05:11",
    "enabled": true,
    "roles": [
        "USER"
    ]
}
```

---

**get user by email**

`curl -L -X GET 'http://localhost:8080/admin/users/by?email=user@gmail.com' --user 'admin@yandex.ru:admin'`

**Response:**

```
{
    "id": 100001,
    "name": "Sergey",
    "email": "user@gmail.com",
    "registered": "2020-02-05T13:05:11",
    "enabled": true,
    "roles": [
        "USER"
    ]
}
```

---

**create user**

`curl -L -X POST 'http://localhost:8080/admin/users' -H 'Content-Type: application/json' --user 'admin@yandex.ru:admin' --data-raw '{"name": "New Name","email": "newemail@yandex.ru","password": "newPass"}'`

**Response:**

```
{
    "id": 100059,
    "name": "New Name",
    "email": "newemail@yandex.ru",
    "registered": "2021-01-01T00:48:58",
    "enabled": true,
    "roles": [
        "USER"
    ]
}
```

---

**update user by id**

`curl -L -X PUT 'http://localhost:8080/admin/users/100001' -H 'Content-Type: application/json' --user 'admin@yandex.ru:admin' --data-raw '{"id": 100001,"name": "Updated Name","email": "updatedemail@yandex.ru","password": "updatedpass"}'`

---

**delete user by id**

`curl -L -X DELETE 'http://localhost:8080/admin/users/100001' --user 'admin@yandex.ru:admin'`
