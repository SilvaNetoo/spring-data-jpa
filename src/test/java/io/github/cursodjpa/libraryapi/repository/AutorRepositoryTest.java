package io.github.cursodjpa.libraryapi.repository;

import io.github.cursodjpa.libraryapi.model.Autor;
import io.github.cursodjpa.libraryapi.model.GeneroLivro;
import io.github.cursodjpa.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1,31));

        var autorSalvo = repository.save(autor);
        System.out.printf("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("e7be1cb2-f7f1-4e04-bba3-a00ca6f2b1ce");

        Optional<Autor> autor = repository.findById(id);

        if(autor.isPresent()){
            Autor autorEncontrado = autor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1 , 30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("9eb5a324-2eb4-4a28-a163-8c2bc311a56e");
        repository.deleteById(id);
    }


    @Test
    public void deleteTest() {
        var id = UUID.fromString("e7be1cb2-f7f1-4e04-bba3-a00ca6f2b1ce");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    public void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1971, 1,31));

        Livro livro = new Livro();
        livro.setIsbn("20887-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O roubo na casa assombrada");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("67890-84874");
        livro2.setPreco(BigDecimal.valueOf(80));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O vento que sobra alto");
        livro2.setDataPublicacao(LocalDate.of(2000, 1, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<Livro>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
//        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listaLivrosAutor() {
        var id = UUID.fromString("8d17b1b3-916e-415a-9385-ee28aebecb90");
        var autor = repository.findById(id).get();

        List<Livro> listaLivros = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);
        autor.getLivros().forEach(System.out::println);
    }

}
