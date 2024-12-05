package io.qleoz12.com.alura.literalura.model;

import javax.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String autor;

    @Column(name = "year_born")
    @Convert(converter = YearConverter.class)  // Use the converter for Year
    private Year YearBorn;

    @Column(name = "year_death")
    @Convert(converter = YearConverter.class)  // Use the converter for Year
    private Year YearDeath;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Year getYearBorn() {
        return YearBorn;
    }

    public void setYearBorn(Year yearBorn) {
        YearBorn = yearBorn;
    }

    public Year getYearDeath() {
        return YearDeath;
    }

    public void setYearDeath(Year yearDeath) {
        YearDeath = yearDeath;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Construtores
    public Autor() {}

    public static boolean possuiAno(Year ano) {
        return ano != null && !ano.equals(Year.of(0));
    }

    public Autor(AutorDTO autorDTO) {
        this.autor = autorDTO.autor();
        this.YearBorn = autorDTO.anoNascimento() != null ? Year.of(autorDTO.anoNascimento()) : null;
        this.YearDeath = autorDTO.anoFalecimento() != null ? Year.of(autorDTO.anoFalecimento()) : null;
    }


    public Autor(String autor, Year anoNascimento, Year anoFalecimento) {
        this.autor = autor;
        this.YearBorn = anoNascimento;
        this.YearDeath = anoFalecimento;
    }

    @Override
    public String toString() {
        String anoNascimentoStr = YearBorn != null ? YearBorn.toString() : "Desconhecido";
        String anoFalecimentoStr = YearDeath != null ? YearDeath.toString() : "Desconhecido";

        return "Autor: " + autor + " (nascido em " + anoNascimentoStr + ", falecido em " + anoFalecimentoStr + ")";
    }
}
