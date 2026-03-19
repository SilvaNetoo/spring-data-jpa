package io.github.cursodjpa.libraryapi.repository;

import io.github.cursodjpa.libraryapi.model.Autor;
import io.github.cursodjpa.libraryapi.model.GeneroLivro;
import io.github.cursodjpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
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
    // select * from livro where dataPublicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL -> referencia as entidades e as propriedades das entidades
    // select l.* from Livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloEPreco();

    /**
     * select a.*
     * from livro as l
     * join autor as a on a.id = l.id_autor
     */
    @Query(" select a from Livro l join l.autor a  ")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao ")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomeDaPropriedade
    );

    @Transactional
    @Modifying
    @Query(" delete from Livro l where l.genero = ?1 ")
    void deleteByGenero(GeneroLivro genero);

    @Transactional
    @Modifying
    @Query(" update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate date);
}
