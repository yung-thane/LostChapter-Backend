# LostChapter-Backend

## Annotations
* @Admin - means only user who has "Admin" role can access these endpoints
* @Customer - means only user who has "Customer" role can access these endpoints
## External API
* The Team utilized Google Books API to populate their own database.

## Endpoints
### Login
>#### GET
* /loginstatus
    * Main way to get User credentials to get their Cart
    * Main way to check whether a User is an "Admin" or a "Customer"

>#### POST
* /login
    * Two Form Params: username and password
    * Generate sessions
* /logout
    * Invalidate sessions

### Register
>#### POST
* /signup

### User Profile
>#### PUT
* /user
    * Updates User Information

>#### DELETE
* /user
    * Deletes User's Account
    * Only when they're logged in

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
* /books/featured
    * Get all books that are featured
    * Based on the 15 most number of quantity of books will be displayed. (This can be changed whether you want to feature less or more books)

>#### POST
* /books (Added feature)
    * Add new Book into the system
    * Has @Admin Annotation
#### PUT
* /books/{id}
    * Updates a book by id
    * Has @Admin Annotation

### Carts
#### GET
>Because a User has a `OneToOne relationship` with the Cart, we can then find the cart Id by using the userId
* /user/{userId}/cart
    * main endpoint for displaying all the books in the cart and the quantity of the books the customer wants to buy
    * Has @Customer Annotation

>#### POST
* /user/{userId}/cart
    * Adding a book into the cart
    * One PathVariable: userId
    * Two Request Params: bookId & quantityToBuy
    * Has @Customer Annotation

#### DELETE
* /user/{userId}/cart
    * It can either delete all the books in the cart or delete a single book in the cart
    * One PathVariable: userId
    * One Request Params: productId
    * Has @Customer Annotation

### Checkout
#### GET
* /order-confirmation/{transactionId}
    * Main way to track all the transactions
    * Has @Customer Annotation
#### POST
* /user/checkout
    * Saves card information and Shipping Information
    * Automatically deducts the card balance and total price of all the books bought
    * Checks if the card exists on the database or not
        * If it does, it will check for the card number, expiration date, and CCV (Security Code) if it matches to the one the user inputs.
            * If it matches, it will use that card, and if not, it will save that card as a new card.
    * Has @Customer Annotation

### Developers' Notes
* Currently the Card System is a built in, hard coded implementation, so whenever there is a new card to be saved to the database, the card balance is set to 10,000.

