package io.github.cursodjpa.libraryapi.repository;

import io.github.cursodjpa.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

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

}
