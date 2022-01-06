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

	@Column(nullable = false, length = 800)
	private String synopsis;

	@Column(nullable = false)
	private String author;

	@ManyToOne
	private Genre genre;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private int year;

	@Column(nullable = false)
	private String edition;

	@Column(nullable = false)
	private String publisher;

	@Column(nullable = false)
	private boolean saleIsActive;

	private double saleDiscountRate;

	@Column(nullable = false)
	private double bookPrice;

	@Column(nullable = false)
	private String bookImage;

	public Book() {
		super();
	}

	public Book(String iSBN, String bookName, String synopsis, String author, Genre genre, int quantity, int year,
			String edition, String publisher, boolean saleIsActive, double saleDiscountRate, double bookPrice,
			String bookImage) {
		super();
		this.ISBN = iSBN;
		this.bookName = bookName;
		this.synopsis = synopsis;
		this.author = author;
		this.genre = genre;
		this.quantity = quantity;
		this.year = year;
		this.edition = edition;
		this.publisher = publisher;

		this.saleIsActive = saleIsActive;
		this.saleDiscountRate = saleDiscountRate;

		this.bookPrice = bookPrice;
		this.bookImage = bookImage;
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

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
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

	public double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ISBN, author, bookId, bookImage, bookName, bookPrice, edition, genre, publisher, quantity,
				saleDiscountRate, saleIsActive, synopsis, year);
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
		return Objects.equals(ISBN, other.ISBN) && Objects.equals(author, other.author) && bookId == other.bookId
				&& Objects.equals(bookImage, other.bookImage) && Objects.equals(bookName, other.bookName)
				&& Double.doubleToLongBits(bookPrice) == Double.doubleToLongBits(other.bookPrice)
				&& Objects.equals(edition, other.edition) && Objects.equals(genre, other.genre)
				&& Objects.equals(publisher, other.publisher) && quantity == other.quantity
				&& Double.doubleToLongBits(saleDiscountRate) == Double.doubleToLongBits(other.saleDiscountRate)
				&& saleIsActive == other.saleIsActive && Objects.equals(synopsis, other.synopsis) && year == other.year;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", ISBN=" + ISBN + ", bookName=" + bookName + ", synopsis=" + synopsis
				+ ", author=" + author + ", genre=" + genre + ", quantity=" + quantity + ", year=" + year + ", edition="
				+ edition + ", publisher=" + publisher + ", saleIsActive=" + saleIsActive + ", saleDiscountRate="
				+ saleDiscountRate + ", bookPrice=" + bookPrice + ", bookImage=" + bookImage + "]";
	}

}
