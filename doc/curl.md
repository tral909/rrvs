# RRVS

Examples of rest api requests shown here.  

Roles:
+ ADMIN - admin/qwqwqw
+ USER - user/1qaz2wsx

### Restaurants

##### get all restaurants
```curl -si http://localhost:8080/restaurants --user admin:qwqwqw```
##### get restaurant by id
```curl -si http://localhost:8080/restaurants/:id --user admin:qwqwqw```
##### get menus by restaurant id
```curl -si http://localhost:8080/restaurants/:id/menus --user user:1qaz2wsx```
##### get dishes by restaurant id and menu id
```curl -si http://localhost:8080/restaurants/:id/menus/:id --user user:1qaz2wsx```
##### get dishes by restaurant id and menu date
```curl -si http://localhost:8080/restaurants/:id/menus/filter?date=yyyy-MM-dd --user user:1qaz2wsx```
##### create restaurant
```curl -si -X POST http://localhost:8080/restaurants --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"name": "Sezam", "phone": "1234567", "address": "Italy, Colizeum, 3"}'```
##### update restaurant by id
```curl -si -X PUT http://localhost:8080/restaurants --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"name": "Sezam2", "phone": "7654321", "address": "Italy, Colizeum, 7"}'```
##### delete restaurant by id
```curl -si -X DELETE http://localhost:8080/restaurants/:id --user admin:qwqwqw```
___

### Users

##### get all users
```curl -si http://localhost:8080/users --user admin:qwqwqw```
##### get user by id
```curl -si http://localhost:8080/users/:id --user admin:qwqwqw```
##### create user
```curl -si -X POST http://localhost:8080/users --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"name": "newuser", "login": "newlogin", "password": "secretkey"}'```
##### update user
```curl -si -X PUT http://localhost:8080/users --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"name": "updateduser", "login": "updatedlogin", "password": "newsecret"}'```
##### delete user by id
```curl -si -X DELETE http://localhost:8080/users/:id --user admin:qwqwqw```
___

### Votes

##### get all votes
```curl -si http://localhost:8080/votes --user user:1qaz2wsx```
##### get vote by id
```curl -si http://localhost:8080/votes/:id --user user:1qaz2wsx```
##### create/update vote
```curl -si -X POST http://localhost:8080/votes --user user:1qaz2wsx -H 'content-type: application/json;charset=utf-8' -d '{"restaurant_id": 4}'```
##### delete vote
```curl -si -X DELETE http://localhost:8080/votes/:id --user user:1qaz2wsx```
___

### Dishes

##### get all dishes
```curl -si http://localhost:8080/dishes --user admin:qwqwqw```
##### get dishes by id
```curl -si http://localhost:8080/dishes/:id --user admin:qwqwqw```
##### create dish
```curl -si -X POST http://localhost:8080/dishes --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"name": "newMeal", "price" : 77}'```
##### update dish
```curl -si -X PUT http://localhost:8080/dishes/:id --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"name": "updatedMeal", "price" : 78}'```
##### delete dish
```curl -si -X DELETE http://localhost:8080/dishes/:id --user admin:qwqwqw```
___

### Menus

##### get all menus
```curl -si http://localhost:8080/menus --user admin:qwqwqw```
##### get menu by id
```curl -si http://localhost:8080/menus/:id --user admin:qwqwqw```
##### create menu
```curl -si -X POST http://localhost:8080/menus --user admin:qwqwqw -H 'content-type: application/json;charset=utf-8' -d '{"date": "2019-02-26", "restaurant_id": 2}'```
##### add dish at menu
```curl -si -X POST http://localhost:8080/menus/:id/dishes/:id --user admin:qwqwqw```
##### delete dish from menu
```curl -si -X DELETE http://localhost:8080/menus/:id/dishes/:id --user admin:qwqwqw```
##### delete menu
```curl -si -X DELETE http://localhost:8080/menus/:id --user admin:qwqwqw```
___