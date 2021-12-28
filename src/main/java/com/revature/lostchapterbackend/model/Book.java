package com.revature.lostchapterbackend.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;

	@Column(nullable = false)
	private String ISBN;

	@Column(nullable = false)
	private String bookName;

	@Column(nullable = false)
	private String synopsis;

	@Column(nullable = false)
	private String author;

	@ManyToOne
	@Column(nullable = false)
	private Genre genreId;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private int year;

	@Column(nullable = false)
	private String edition;

	@Column(nullable = false)
	private String publisher;

	@Column(nullable = false)
	private String bindingType;

	@Column(nullable = false)
	private boolean saleIsActive;

	private double saleDiscountRate;

	@Column(nullable = false)
	private String condition;

	@Column(nullable = false)
	private double bookPrice;

//@Column(nullable = false)
//private String bookImage

	public Book() {
		super();
	}

	public Book(String iSBN, String bookName, String synopsis, String author, Genre genreId, int quantity, int year,
			String edition, String publisher, String bindingType, boolean saleIsActive, double saleDiscountRate,
			String condition, double bookPrice) {
		super();
		ISBN = iSBN;
		this.bookName = bookName;
		this.synopsis = synopsis;
		this.author = author;
		this.genreId = genreId;
		this.quantity = quantity;
		this.year = year;
		this.edition = edition;
		this.publisher = publisher;
		this.bindingType = bindingType;
		this.saleIsActive = saleIsActive;
		this.saleDiscountRate = saleDiscountRate;
		this.condition = condition;
		this.bookPrice = bookPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ISBN, author, bindingType, bookId, bookName, bookPrice, condition, edition, genreId,
				publisher, quantity, saleDiscountRate, saleIsActive, synopsis, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(ISBN, other.ISBN) && Objects.equals(author, other.author)
				&& Objects.equals(bindingType, other.bindingType) && bookId == other.bookId
				&& Objects.equals(bookName, other.bookName)
				&& Double.doubleToLongBits(bookPrice) == Double.doubleToLongBits(other.bookPrice)
				&& Objects.equals(condition, other.condition) && Objects.equals(edition, other.edition)
				&& Objects.equals(genreId, other.genreId) && Objects.equals(publisher, other.publisher)
				&& quantity == other.quantity
				&& Double.doubleToLongBits(saleDiscountRate) == Double.doubleToLongBits(other.saleDiscountRate)
				&& saleIsActive == other.saleIsActive && Objects.equals(synopsis, other.synopsis) && year == other.year;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Genre getGenreId() {
		return genreId;
	}

	public void setGenreId(Genre genreId) {
		this.genreId = genreId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBindingType() {
		return bindingType;
	}

	public void setBindingType(String bindingType) {
		this.bindingType = bindingType;
	}

	public boolean isSaleIsActive() {
		return saleIsActive;
	}

	public void setSaleIsActive(boolean saleIsActive) {
		this.saleIsActive = saleIsActive;
	}

	public double getSaleDiscountRate() {
		return saleDiscountRate;
	}

	public void setSaleDiscountRate(double saleDiscountRate) {
		this.saleDiscountRate = saleDiscountRate;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", ISBN=" + ISBN + ", bookName=" + bookName + ", synopsis=" + synopsis
				+ ", author=" + author + ", genreId=" + genreId + ", quantity=" + quantity + ", year=" + year
				+ ", edition=" + edition + ", publisher=" + publisher + ", bindingType=" + bindingType
				+ ", saleIsActive=" + saleIsActive + ", saleDiscountRate=" + saleDiscountRate + ", condition="
				+ condition + ", bookPrice=" + bookPrice + "]";
	}

}
