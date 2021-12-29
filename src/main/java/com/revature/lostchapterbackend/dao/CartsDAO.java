package com.revature.lostchapterbackend.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Genre;

@Repository
public class CartsDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Carts insertToCart(Carts currentCart, Book b, Genre g) {

		Session session = em.unwrap(Session.class);

		session.saveOrUpdate(g);

		session.save(b);

//		session.saveOrUpdate(currentCart);

		return currentCart;
	}

	public Carts insertToCart(Carts currentCart, BookToBuy booksToBeBought) {
		Session session = em.unwrap(Session.class);

		session.saveOrUpdate(booksToBeBought);

		session.saveOrUpdate(currentCart);

		return currentCart;
	}

	@Transactional
	public Carts selectACartById(int id) throws NoResultException {
		Carts c;
		try {
			String query = "SELECT c FROM Carts c WHERE c.cartId = :id";
			TypedQuery<Carts> typedQuery = em.createQuery(query, Carts.class);
			c = typedQuery.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultException("No exisiting cart with an id of " + id);
		}
		return c;
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
