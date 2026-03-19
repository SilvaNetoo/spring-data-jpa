package io.github.cursodjpa.libraryapi.repository;

import io.github.cursodjpa.libraryapi.model.Autor;
import io.github.cursodjpa.libraryapi.model.GeneroLivro;
import io.github.cursodjpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;
    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 22));

        Autor autor = autorRepository.findById(UUID.fromString("e7be1cb2-f7f1-4e04-bba3-a00ca6f2b1ce")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorLivro() {
        UUID id = UUID.fromString("09b7b891-9706-4c6e-bee6-dffb47a2e1e8");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("da7ce258-a273-4fa5-a674-a6da6de2206e");
        var maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);
        repository.save(livroParaAtualizar);
    }

    @Test
    public void deleteById() {
        UUID id = UUID.fromString("ed2f957a-218d-4b28-8014-e11b627d395c");
        repository.deleteById(id);
    }

    @Test
    void pesquisaPorTituloTeste() {
        List<Livro> lista = repository.findByTitulo("O roubo na casa assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBNTeste() {
        List<Livro> lista = repository.findByIsbn("67890-84874");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco() {
//        var preco = new BigDecimal("204.00");
        var preco = BigDecimal.valueOf(204.00);
        List<Livro> lista = repository.findByTituloAndPreco("O roubo na casa assombrada", preco);
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloOuIsbn() {
        List<Livro> lista = repository.findByTituloOrIsbn("O vento que sobra alto", "67890-84874");
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL() {
        var resultado = repository.listarTodosOrdenadoPorTituloEPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosQueryJPQL() {
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTItulosNaoRepetidosDosLivros() {
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileiros() {
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQUeryParam() {
        var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletaPorGeneroTest() {
        repository.deleteByGenero(GeneroLivro.FICCAO);
    }

    @Test
    void updateDataPublicacaoTest() {
        repository.updateDataPublicacao(LocalDate.of(2000,1,1));
    }

}