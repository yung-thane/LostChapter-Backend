package com.revature.lostchapterbackend.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;


@Repository
public class CartsDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Carts insertToCart(Carts currentCart, BookToBuy booksToBeBought) {
		
		Session session = em.unwrap(Session.class);

		session.saveOrUpdate(booksToBeBought);

		session.saveOrUpdate(currentCart);

		return currentCart;
	}

	@Transactional
	public Carts deleteAProductInTheCart(Carts currentCart, int bookToBeDeleted) {
		
		Session session = em.unwrap(Session.class);

		BookToBuy toBeDeleted = session.find(BookToBuy.class, bookToBeDeleted);

		session.remove(toBeDeleted);

		session.merge(currentCart);

		return currentCart;
	}

}
