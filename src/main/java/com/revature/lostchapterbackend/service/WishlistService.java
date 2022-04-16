package com.revature.lostchapterbackend.service;

import com.revature.lostchapterbackend.dao.BookToBuyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private BookService bs;

    @Autowired
    private BookToBuyDAO btbd;


}
