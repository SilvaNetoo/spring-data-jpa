package io.github.cursodjpa.libraryapi.repository;

import io.github.cursodjpa.libraryapi.model.Autor;
import io.github.cursodjpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    //Query Method
    List<Livro> findByAutor(Autor autor);
    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);
    // select * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);
    // select * from livro where titulo = titulo && preco = preco
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    // select * from livro where titulo = titulo || isbn = isbn
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);
}
