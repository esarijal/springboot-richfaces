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
        if(todoList == null){
            todoList = todoRepository.findAll().stream().filter(t->!t.getCompleted()).collect(Collectors.toList());
        }
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

    public void clearMessages(){

        List<Todo> selected = todoList.stream().filter(Todo::getCompleted).collect(Collectors.toList());
        todoRepository.save(selected);
        logger.debug("completed {} todo", selected.size());

    }

    public void loadTodoList(){
        switch (filter){
            case "0":
                todoList = todoRepository.findAll();
                break;
            case "1":
                todoList = todoRepository.findAllByCompleted(false);
                break;
            case "2":
                todoList = todoRepository.findAllByCompleted(true);
                break;
        }
    }

}
