package io.qleoz12.com.alura.literalura.repository;

import io.qleoz12.com.alura.literalura.model.Autor;
import io.qleoz12.com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;

public interface LivroRepository extends JpaRepository<Book, Long> {

    @Query("SELECT l FROM Book l WHERE LOWER(l.titulo) = LOWER(:titulo)")
    List<Book> findByTitulo(@Param("titulo") String titulo);

    @Query("SELECT a FROM Autor a WHERE a.YearBorn <= :ano AND (a.YearDeath IS NULL OR a.YearDeath >= :ano)")
    List<Autor> findAutoresVivos(@Param("ano") Year ano);

    @Query("SELECT a FROM Autor a WHERE a.YearBorn = :ano AND (a.YearDeath IS NULL OR a.YearDeath >= :ano)")
    List<Autor> findAutoresVivosRefinado(@Param("ano") Year ano);

    @Query("SELECT a FROM Autor a WHERE a.YearBorn <= :ano AND a.YearDeath = :ano")
    List<Autor> findAutoresPorAnoDeMorte(@Param("ano") Year ano);

    @Query("SELECT l FROM Book l WHERE l.idioma LIKE %:idioma%")
    List<Book> findByIdioma(@Param("idioma") String idioma);

}
