package com.revature.lostchapterbackend.dto;

import java.util.Objects;

import com.revature.lostchapterbackend.model.Genre;

public class AddOrUpdateBookDTO {

	private String ISBN;
	private String bookName;
	private String synopsis;
	private String author;
	private int genre;
	private int quantity;
	private int year;
	private String edition;
	private String publisher;
	private boolean saleIsActive;
	private double saleDiscountRate;
	private double bookPrice;
	private String bookImage;

	public AddOrUpdateBookDTO() {
		super();
	}

	public AddOrUpdateBookDTO(String iSBN, String bookName, String synopsis, String author, int genre, int quantity, int year,
			String edition, String publisher, boolean saleIsActive, double saleDiscountRate, double bookPrice,
			String bookImage) {
		super();
		ISBN = iSBN;
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

	public int getGenre() {
		return genre;
	}

	public void setGenre(int genre) {
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
		return Objects.hash(ISBN, author, bookImage, bookName, bookPrice, edition, genre, publisher, quantity,
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
		AddOrUpdateBookDTO other = (AddOrUpdateBookDTO) obj;
		return Objects.equals(ISBN, other.ISBN) && Objects.equals(author, other.author)
				&& Objects.equals(bookImage, other.bookImage) && Objects.equals(bookName, other.bookName)
				&& Double.doubleToLongBits(bookPrice) == Double.doubleToLongBits(other.bookPrice)
				&& Objects.equals(edition, other.edition) && genre == other.genre
				&& Objects.equals(publisher, other.publisher) && quantity == other.quantity
				&& Double.doubleToLongBits(saleDiscountRate) == Double.doubleToLongBits(other.saleDiscountRate)
				&& saleIsActive == other.saleIsActive && Objects.equals(synopsis, other.synopsis) && year == other.year;
	}

	@Override
	public String toString() {
		return "AddBookDTO [ISBN=" + ISBN + ", bookName=" + bookName + ", synopsis=" + synopsis + ", author=" + author
				+ ", genre=" + genre + ", quantity=" + quantity + ", year=" + year + ", edition=" + edition
				+ ", publisher=" + publisher + ", saleIsActive=" + saleIsActive + ", saleDiscountRate="
				+ saleDiscountRate + ", bookPrice=" + bookPrice + ", bookImage=" + bookImage + "]";
	}
	
	

}