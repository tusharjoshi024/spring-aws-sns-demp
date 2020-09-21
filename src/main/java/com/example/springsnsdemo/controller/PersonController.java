package com.example.springsnsdemo.controller;

import com.example.springsnsdemo.model.Person;
import com.example.springsnsdemo.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @PostMapping
    public Person save(@RequestBody Person person){
        return personService.save(person);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable Long id, @RequestBody Person person){
        return personService.update(id,person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/contact")
    public void textPerson(@PathVariable Long id, @RequestParam String message){
        personService.textPerson(id,message);
    }
}
