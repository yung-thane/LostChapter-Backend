package com.revature.lostchapterbackend.service;

import com.revature.lostchapterbackend.dao.BookToBuyDAO;
import com.revature.lostchapterbackend.dao.CartsDAO;
import com.revature.lostchapterbackend.dao.WishlistDAO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.OutOfStockException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class WishlistService {
    /**
     * To-do list:
     * - Create CRUD (create- read - update - delete)
     * - Go back to the WishlistDAO to create paging and sorting and then implements them in this class
     */

    @Autowired
    private BookService bs;

    @Autowired
    private CartsDAO cd; // using JPA Repository

    @Autowired
    private BookToBuyDAO btbd;

    @Autowired
    private WishlistDAO wld;

//    public WishlistService(BookService bs, BookToBuyDAO btbd, WishlistDAO wld) {
//        this.bs = bs;
//        this.btbd = btbd;
//        this.wld = wld;
//    }

    public WishlistService(BookService bs, CartsDAO cd, BookToBuyDAO btbd, WishlistDAO wld) {
        this.bs = bs;
        this.cd = cd;
        this.btbd = btbd;
        this.wld = wld;
    }

    public Wishlist getWishlistById(String id) {
        try {

            int wishlistId = Integer.parseInt(id);
            return wld.findById(wishlistId).get();
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("The Id entered must be an int.");
        }
    }

//    public Wishlist addBooksToWishlist(Wishlist currentWishlist, String userId, String bookId, String quantityToBuy)
//            throws OutOfStockException, BookNotFoundException {
//
//        // checking if wishlist exist
//        currentWishlist = this.getWishlistById(userId);
//
//        Book b = bs.getBookById(bookId);
//
//        if (b.getQuantity() <= 0) {
//            throw new OutOfStockException("Currently Out of Stock...");
//        }
////        int amountToBuy = Integer.parseInt(quantityToBuy);
////
////        if (amountToBuy <= 0) {
////            throw new InvalidParameterException("Quantity to Buy cannot be less than or equal to zero!");
////        }
//        BookToBuy booksToBeBought = new BookToBuy(b);
////
//        List<BookToBuy> currentBooksInTheCart = currentWishlist.getBooksToBuy();
////
//        boolean checkBook = checkBookInTheCart(currentBooksInTheCart, b);
//
//        if (checkBook == false) {
//            currentBooksInTheCart.add(booksToBeBought);
//            currentCart.setBooksToBuy(currentBooksInTheCart);
//        } else if (checkBook == true) {
//            for (BookToBuy b1 : currentBooksInTheCart) {
//                if (b1.getBooks() == b) {
//                    b1.setQuantityToBuy(b1.getQuantityToBuy() + amountToBuy);
//                    booksToBeBought = b1;
//                }
//            }
//        }
//        wld.saveAndFlush(booksToBeBought);
//        btbd.saveAndFlush(booksToBeBought);
//        return wld.saveAndFlush(currentWishlist);
//        return cd.saveAndFlush(currentCart);
//
//    }


}
