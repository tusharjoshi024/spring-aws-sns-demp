package com.example.springsnsdemo.service;

import com.example.springsnsdemo.client.TextMessageClient;
import com.example.springsnsdemo.model.Person;
import com.example.springsnsdemo.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final TextMessageClient messageClient;

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        return personRepository.saveAndFlush(person);
    }

    public Person getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person update(Long id, Person person) {
        if(personRepository.findById(id).isPresent()){
            person.setId(id);
        }
        return personRepository.saveAndFlush(person);
    }

    public void delete(Long id) {
        if(personRepository.findById(id).isPresent()){
            personRepository.deleteById(id);
        }
    }

    public void textPerson(Long id, String message) {
        if(this.getPerson(id)==null || this.getPerson(id).getContact().isEmpty()){
            //handle the error here
        }
        else {
            messageClient.sendSMS(this.getPerson(id).getContact(),message);
        }
    }
}
