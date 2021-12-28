# LostChapter-Backend

## Endpoints
### Login
>#### GET
* /loginstatus
    * Main way to get User credentials to get their Cart

>#### POST
* /login
    * Two Form Params: username and password
    * Generate sessions/jwt(?)
* /logout
    * Invalidate sessions/jwt(?)

### Register
>#### POST
* /signup

### User Profile
>####

### Books (Product)
>#### GET
* /books
    * Get all books
* /books
    * Get a book by bookId (@RequestParam)
    * Get a book by ISBN (@RequestParam)
    * Get a book that matches certain characters/words
        * To achieve this, I think there would be an if/else statement:
            * if bookId == bookId, use a Service for looking a book by its id;
            * else if book isbn == isbn, use a Service that looks for a book by its isbn number;
            * else if book contains specific words/characters, use a Service for looking a book that matches certain characters/words
>#### POST
* /books (Added feature, not stated on MVP, skip for now)
    * Add new Book into the system: (JSON Format)

### Carts
>#### POST
* /user/{userId}/cart?bookId=&quantityToBuy=
    * Because a User is connected to a Cart, we can then find the cart Id by using the userId
    * Adding a book into the cart
    * One PathVariable: userId
    * Two Request Params: bookId & quantityToBuy

#### DELETE
* /user/{userId}/cart?productId=
    * Deleting a book in the cart
    * One PathVariable: userId
    * One Request Params: productId
