package com.bustanil.controller;

import com.bustanil.model.Todo;
import com.bustanil.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ViewScoped
public class HelloController {

    @Autowired
    private TodoRepository todoRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String message;
    private List<Todo> todoList;
    private String filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Todo> getTodoList(){

        loadTodoList();
        return todoList;
    }

    public void onChangedListener(ValueChangeEvent e){
        String task = (String) e.getNewValue();
        if(task.isEmpty())
            return;

        logger.debug("Will be a new task : {}", task);
        Todo todo = new Todo();
        todo.setTask(task);

        todoRepository.save(todo);
        logger.debug("Task {} was saved", task );

    }

    public void clearCompleted(){

        List<Todo> selected = todoRepository.findAllByCompleted(true);
        todoRepository.delete(selected);
        logger.debug("deleted {} completed todo", selected.size());

    }

    public void loadTodoList(){
        if(filter == null)
            filter = "0";
        switch (filter){
            case "1":
            todoList = todoRepository.findAllByCompleted(false);
            break;
            case "2":
            todoList = todoRepository.findAllByCompleted(true);
            break;
            default:
                todoList = todoRepository.findAll();
                break;
        }
    }

    public void update(Long id, Boolean currentStatus){
        Todo one = todoRepository.findOne(id);
        one.setCompleted(!currentStatus);
        todoRepository.save(one);
    }

    public void updateTask(Long id, String newTask){
        Todo one = todoRepository.findOne(id);
        one.setTask(newTask);
        todoRepository.save(one);
    }
}
