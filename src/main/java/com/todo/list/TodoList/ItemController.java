package com.todo.list.TodoList;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
  private final ItemRepository repository;

  ItemController(ItemRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/todo")
  List<Item> all() {
    return repository.findAll();
  }

  @PostMapping("/todo")
  Item newItem(@RequestBody Item newItem) {
    return repository.save(newItem);
  }

  @GetMapping("/todo/{id}")
  Item one(@PathVariable Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ItemNotFoundException(id));
  }

  @PutMapping("/todo/{id}")
  Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
    return repository.findById(id)
      .map(item -> {
        item.setTitle(newItem.getTitle());
        item.setDescription(newItem.getDescription());
        return repository.save(newItem);
      })
      .orElseGet(() -> {
        newItem.setId(id);
        return repository.save(newItem);
      });
  }

  @DeleteMapping("/todo/{id}")
  void deleteItem(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
