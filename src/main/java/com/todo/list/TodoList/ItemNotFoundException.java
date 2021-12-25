package com.todo.list.TodoList;

public class ItemNotFoundException extends RuntimeException {
  ItemNotFoundException(Long id) {
    super("Could not find todo item " + id);
  }
}
