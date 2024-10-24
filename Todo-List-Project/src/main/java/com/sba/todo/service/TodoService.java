package com.sba.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sba.todo.vo.Todo;

@Service
public class TodoService {

    private final List<Todo> todoList;

    public TodoService(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public void addTodo(Todo todo) {
        todo.setId(todoList.size());
        todoList.add(todo);
    }

    public List<Todo> getTodoList(){
        return todoList;
    }

    public void deleteTodo(int id) {
        int index = 0;
        for(Todo todo: todoList) {
            if (todo.getId() == id) {
                break;
            }
            index++;
        }

        todoList.remove(index);
    }
}
