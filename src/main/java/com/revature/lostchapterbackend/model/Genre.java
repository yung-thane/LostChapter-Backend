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
	private int Id;

	@Column(nullable = false)
	private String genre;

	public Genre() {
		super();
	}

	public Genre(int Id, String genre) {
		super();
		this.Id = Id;
		this.genre = genre;
	}

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public int hashCode() {
<<<<<<< HEAD
		return Objects.hash(genre, Id);
=======
		return Objects.hash(Id, genre);
>>>>>>> main
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
<<<<<<< HEAD
		return Objects.equals(genre, other.genre) && Id == other.Id;
=======
		return Id == other.Id && Objects.equals(genre, other.genre);
>>>>>>> main
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		return "Genre [genreId=" + Id + ", genre=" + genre + "]";
	}
=======
		return "Genre [Id=" + Id + ", genre=" + genre + "]";
	}

>>>>>>> main
}
