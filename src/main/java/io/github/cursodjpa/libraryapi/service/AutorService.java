package io.github.cursodjpa.libraryapi.service;

import io.github.cursodjpa.libraryapi.controller.dto.AutorDTO;
import io.github.cursodjpa.libraryapi.model.Autor;
import io.github.cursodjpa.libraryapi.repository.AutorRepository;
import io.github.cursodjpa.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;

    public AutorService(AutorRepository autorRepository, AutorValidator validator){
        this.repository = autorRepository;
        this.validator = validator;
    }

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return this.repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessaário que o autor ja esteja cadastrado na base");
        }
        validator.validar(autor);
        this.repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null) {
            return repository.findByNome(nome);
        }

        if(nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

}
