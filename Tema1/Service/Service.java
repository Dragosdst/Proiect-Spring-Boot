package Tema1.Service;

import Tema1.Person.Person;
import Tema1.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private static PersonRepository personRepository;

    public List<Person> getAllUsers() {
        return personRepository.findAll();
    }

    public Optional<Person> getUserById(Long id) {
        return personRepository.findById(id);
    }

    public Person createUser(Person user) {
        return personRepository.save(user);
    }

    public void deleteUser(Long id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> updateUser(Long id, Person updatedUser) {
        Optional<Person> existingUserOptional = personRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            Person existingUser = existingUserOptional.get();
            existingUser.setNume(updatedUser.getNume());
            existingUser.setPrenume(updatedUser.getPrenume());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setVarsta(updatedUser.getVarsta());
            personRepository.save(existingUser);
            return Optional.of(existingUser);
        } else {
            return Optional.empty();
        }
    }
}









