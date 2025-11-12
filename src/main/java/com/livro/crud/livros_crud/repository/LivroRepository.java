package com.livro.crud.livros_crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    public List<Livro> findByTitulo(String titulo);

    public List<Livro> findByAno(int ano);

    public List<Livro> findByAutor(Autor autor);

    @Query("FROM Livro l WHERE l.ano > :ano")
    public List<Livro> findByAnoMaiorQue(int ano);
}
 