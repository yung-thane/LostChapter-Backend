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
>#### PUT
* /update
    * Updates User Information

>#### DELETE
* /delete
    * Deletes User's Account(?)

### Books (Product)
>#### GET
* /books
    * Get all books
* /books/{bookId}
    * Get a book by bookId (@PathVariable)
* /books/ISBN (stretch goal)
    * Get a book by ISBN (@RequestParam)
* /books/search/{keyword} (@PathVariable)
    * Get a book that matches certain characters/words
* /books/genre/{genreId}
    * Get a book by genre id (@PathVariable)
* /books/sales
    * Get all books on sale

>#### POST
* /books (Added feature, not stated on MVP, skip for now)
    * Add new Book into the system: (JSON Format)

### Carts
#### GET
>Because a User has a `OneToOne relationship` with the Cart, we can then find the cart Id by using the userId
* /user/{userId}/cart
    * main endpoint for displaying all the books in the cart and the quantity of the books the customer wants to buy

>#### POST
* /user/{userId}/cart?bookId=&quantityToBuy=
    * Adding a book into the cart
    * One PathVariable: userId
    * Two Request Params: bookId & quantityToBuy

#### DELETE
* /user/{userId}/cart?productId=
    * Deleting a book in the cart
    * One PathVariable: userId
    * One Request Params: productId
	
## excuse to push. Testing Jenkins
