package com.revature.lostchapterbackend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Carts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

//	@OneToOne
//	private User user;

	@OneToMany
	private List<BookToBuy> booksToBuy;

	public Carts() {
		super();
	}

	public Carts(List<BookToBuy> booksToBuy) {
		super();
		this.booksToBuy = booksToBuy;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public List<BookToBuy> getBooksToBuy() {
		return booksToBuy;
	}

	public void setBooksToBuy(List<BookToBuy> booksToBuy) {
		this.booksToBuy = booksToBuy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(booksToBuy, cartId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carts other = (Carts) obj;
		return Objects.equals(booksToBuy, other.booksToBuy) && cartId == other.cartId;
	}

	@Override
	public String toString() {
		return "Carts [cartId=" + cartId + ", booksToBuy=" + booksToBuy + "]";
	}

}
