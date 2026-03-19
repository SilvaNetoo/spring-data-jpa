package io.github.cursodjpa.libraryapi.service;

import io.github.cursodjpa.libraryapi.model.Autor;
import io.github.cursodjpa.libraryapi.model.GeneroLivro;
import io.github.cursodjpa.libraryapi.model.Livro;
import io.github.cursodjpa.libraryapi.repository.AutorRepository;
import io.github.cursodjpa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void executar() {
        Autor autor = new Autor();
        autor.setNome("Teste Geralt");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2022,02,05));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Teste Livro do Geralt");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 22));

        livro.setAutor(autor);
        livroRepository.save(livro);


        if(autor.getNome().equals("Teste Geralt")){
            throw new RuntimeException("Rollback!");
        }
    }

}
