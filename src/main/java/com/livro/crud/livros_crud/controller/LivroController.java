package com.livro.crud.livros_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.service.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController {
    
    @Autowired
    private LivroService livroService;
    
    @PostMapping("/save")
    public ResponseEntity<Livro> saveLivro(@RequestBody Livro livro){
        try {
            Livro save = livroService.saveLivro(livro);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Livro>> findAll(){
        try {
            List<Livro> findAll = this.livroService.findAllLivros();
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id){
        try {
            Livro findbyid = this.livroService.findByIdLivro(id);
            if (findbyid == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(findbyid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro){
        try {
            Livro update = this.livroService.update(id, livro);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            this.livroService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findByTitulo")
    public ResponseEntity<List<Livro>> findByTitulo(@RequestParam String titulo){
        try {
            List<Livro> lista = this.livroService.findByTitulo(titulo);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAno")
    public ResponseEntity<List<Livro>> findByAno(@RequestParam int ano){
        try {
            List<Livro> lista = this.livroService.findByAno(ano);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAutor")
    public ResponseEntity<List<Livro>> findByAno(@RequestParam long idAutor){
        try {
            List<Livro> lista = this.livroService.findByAutor(idAutor);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAnoMaiorQue")
    public ResponseEntity<List<Livro>> findByAnoMaiorQue(@RequestParam int ano){
        try {
            List<Livro> lista = this.livroService.findByAnoMaiorQue(ano);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
