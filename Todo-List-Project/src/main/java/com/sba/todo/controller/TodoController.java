package com.sba.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import com.sba.todo.service.TodoService;
import com.sba.todo.vo.Result;
import com.sba.todo.vo.Todo;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    @ResponseBody
    public List<Todo> getTodoList(){
        List<Todo> list = todoService.getTodoList();
        return list;
    }

    @PostMapping("/todo")
    @ResponseBody
    public Object addTodo(@RequestBody Todo todoParam) {
        Todo todo = new Todo(todoParam.getContent());
        todoService.addTodo(todo);
        return new Result(200, "Success");
    }

    @DeleteMapping("/todo/{id}")
    public Object deleteTodo(@PathVariable("id") String id) {
        todoService.deleteTodo(Integer.parseInt(id));
        return new Result(200, "Success");
    }

}
