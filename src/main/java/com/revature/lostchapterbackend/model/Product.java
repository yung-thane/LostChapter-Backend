package com.revature.lostchapterbackend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;

	private String bookTitle;
	private String author;
	private String publisher;
	private String Synopsis;

	private int year; // Year published?
	private String edition;
	@ManyToOne
	private Genre genre;

	private String bindingType; // options are hardcover/softcover/ebook
	@ManyToOne
	private Categories category;
	private String condition; // new or used (Used = set price? Or originalPrice x percentage ?)
	private boolean isOnSale;
	private double buyPrice;
	private double rentPrice;

	public Product() {
		super();
	}

	public Product(String bookTitle, String author, String publisher, String synopsis, int year, String edition,
			Genre genre, String bindingType, Categories category, String condition, boolean isOnSale, double buyPrice,
			double rentPrice) {
		super();
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		Synopsis = synopsis;
		this.year = year;
		this.edition = edition;
		this.genre = genre;
		this.bindingType = bindingType;
		this.category = category;
		this.condition = condition;
		this.isOnSale = isOnSale;
		this.buyPrice = buyPrice;
		this.rentPrice = rentPrice;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getSynopsis() {
		return Synopsis;
	}

	public void setSynopsis(String synopsis) {
		Synopsis = synopsis;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getBindingType() {
		return bindingType;
	}

	public void setBindingType(String bindingType) {
		this.bindingType = bindingType;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean isOnSale() {
		return isOnSale;
	}

	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public double getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(double rentPrice) {
		this.rentPrice = rentPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Synopsis, author, bindingType, bookId, bookTitle, buyPrice, category, condition, edition,
				genre, isOnSale, publisher, rentPrice, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(Synopsis, other.Synopsis) && Objects.equals(author, other.author)
				&& Objects.equals(bindingType, other.bindingType) && bookId == other.bookId
				&& Objects.equals(bookTitle, other.bookTitle)
				&& Double.doubleToLongBits(buyPrice) == Double.doubleToLongBits(other.buyPrice)
				&& Objects.equals(category, other.category) && Objects.equals(condition, other.condition)
				&& Objects.equals(edition, other.edition) && Objects.equals(genre, other.genre)
				&& isOnSale == other.isOnSale && Objects.equals(publisher, other.publisher)
				&& Double.doubleToLongBits(rentPrice) == Double.doubleToLongBits(other.rentPrice) && year == other.year;
	}

	@Override
	public String toString() {
		return "Product [bookId=" + bookId + ", bookTitle=" + bookTitle + ", author=" + author + ", publisher="
				+ publisher + ", Synopsis=" + Synopsis + ", year=" + year + ", edition=" + edition + ", genre=" + genre
				+ ", bindingType=" + bindingType + ", category=" + category + ", condition=" + condition + ", isOnSale="
				+ isOnSale + ", buyPrice=" + buyPrice + ", rentPrice=" + rentPrice + "]";
	}

}
