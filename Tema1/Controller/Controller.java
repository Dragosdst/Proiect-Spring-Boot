package Tema1.Controller;


import Tema1.Repository.PersonRepository;
import Tema1.Person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/persoane")
public class Controller {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/ToatePersoanele")
    public List<Person> aduToatePersoanele() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(personRepository.findById(id));
    }
//am Schimbat Person cu ? - indică că poți să returnezi orice tip de obiect în răspuns, inclusiv un șir (String).
// Prin urmare, utilizarea .body("mesaj") este validă și va furniza un răspuns cu un corp de tip șir.

    // un boolean pentru a ține evidența dacă expresia regulată [a-zA-Z]+
// (care verifică dacă numele conține doar litere) este adevărată sau falsă.
    @PostMapping("/creare")
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        boolean esteValid = person.getNume().length() >= 3 && person.getNume().matches("[a-zA-Z]+");
        if (!esteValid) {
            return ResponseEntity.badRequest().body("Numele trebuie sa aiba cel putin 3 caractere!");
        }
        if (person.getVarsta().longValue() < 18) {
            return ResponseEntity.badRequest().body("Trebuie sa aveti peste 18 ani!");
        }
        Person savePerson = personRepository.save(person);
        return ResponseEntity.ok(savePerson);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Person> updateUser(@PathVariable Long id, @RequestBody Person updatedUser) {
        Optional<Person> existingUserOptional = personRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            Person userExistent = existingUserOptional.get();
            userExistent.setNume(updatedUser.getNume());
            userExistent.setPrenume(updatedUser.getPrenume());
            userExistent.setEmail(updatedUser.getEmail());
            userExistent.setVarsta(updatedUser.getVarsta());
            personRepository.save(userExistent);
            return ResponseEntity.ok(userExistent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



