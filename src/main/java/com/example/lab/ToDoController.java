package com.example.lab;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    private final ToDoRepository toDoRepository;
    
    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }
    
    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(id);
        if (toDoOptional.isPresent()) {
            return ResponseEntity.ok(toDoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ToDo createToDo(@RequestBody ToDo toDo) {
        return toDoRepository.save(toDo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo updatedToDo) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(id);
        if (toDoOptional.isPresent()) {
            ToDo existingToDo = toDoOptional.get();
            existingToDo.setTitle(updatedToDo.getTitle());
            existingToDo.setDescription(updatedToDo.getDescription());
            existingToDo.setCompleted(updatedToDo.isCompleted());
            return ResponseEntity.ok(toDoRepository.save(existingToDo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        Optional<ToDo> toDoOptional = toDoRepository.findById(id);
        if (toDoOptional.isPresent()) {
            toDoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/title/{title}")
    public List<ToDo> getToDosByTitle(@PathVariable String title) {
    return toDoRepository.findByTitle(title);
}

}
