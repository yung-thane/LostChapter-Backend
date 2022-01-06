package com.revature.lostchapterbackend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Carts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;

	@OneToOne
	private Users user;

	@OneToMany
	private List<BookToBuy> booksToBuy;

	public Carts() {
		super();
	}
	
	public Carts(Users user) {
		super();
		this.user = user;
	}

	public Carts(List<BookToBuy> booksToBuy) {
		super();
		this.booksToBuy = booksToBuy;
	}

	public Carts(int cartId, Users user, List<BookToBuy> booksToBuy) {
		super();
		this.cartId = cartId;
		this.user = user;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(booksToBuy, cartId, user);
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
		return Objects.equals(booksToBuy, other.booksToBuy) && cartId == other.cartId
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Carts [cartId=" + cartId + ", user=" + user + ", booksToBuy=" + booksToBuy + "]";
	}

}
