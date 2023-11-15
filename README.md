# Overview

This project is a simple CRUD application <b>with minimum dependencies</b>, that allows you to perform a set of operations to manage cats.

After launching the application, two tables will be created in the database: cats and owners.

<hr>

# Setup project and run the application

## Build Requirements

⇒ Java 17+<br>
⇒ Maven 3.9.5+<br>
⇒ Docker-machine<br>

__Clone:__ git clone https://github.com/PanDubovskij/crud_animals.git

__Run:__

+ ```cd ./crud-animals```
+ ```docker compose up -d --build```

__Stop:__

+ ```docker compose down```

<hr>

# Usage

## Create:

* For create new cat use: POST ``` http://localhost:8080/cats```

  With such JSON body:

    ```
    {
    "name": "cat",
    "color": "black",
    "weight": 5,
    "height": 20,
    "owner-name": "jan",
    "owner-age": 21
    }

  ```

## Read:


* To find all cats use: GET ```http://localhost:8080/cats```

## Update:

* For update cat use: PUT ``` http://localhost:8080/cats```

  With such JSON body:

    ```
     {
    "id" : 4,
    "name": "cat",
    "weight": 8,
    "height": 20,
    "owner-name": "olga",
    "owner-age": 20
    }

  ```

## Delete:

* For delete cat use: DELETE ```http://localhost:8080/cats?id=4```
