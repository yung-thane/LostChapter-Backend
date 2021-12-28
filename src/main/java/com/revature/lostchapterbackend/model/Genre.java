package com.revature.lostchapterbackend.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Genre {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int genreId;

@Column(nullable = false)
private String genre;

public Genre() {
	super();
}

public Genre(int genreId, String genre) {
	super();
	this.genreId = genreId;
	this.genre = genre;
}

public int getGenreId() {
	return genreId;
}

public void setGenreId(int genreId) {
	this.genreId = genreId;
}

public String getGenre() {
	return genre;
}

public void setGenre(String genre) {
	this.genre = genre;
}

@Override
public int hashCode() {
	return Objects.hash(genre, genreId);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Genre other = (Genre) obj;
	return Objects.equals(genre, other.genre) && genreId == other.genreId;
}

@Override
public String toString() {
	return "Genre [genreId=" + genreId + ", genre=" + genre + "]";
}




}

