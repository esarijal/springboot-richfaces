package com.bustanil.controller;

import com.bustanil.model.Todo;
import com.bustanil.repository.TodoRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 03-Apr-17.
 */
@ViewScoped
@Controller
@Transactional
public class TodoController implements InitializingBean {

    @Autowired
    TodoRepository todoRepository;

    private String newTask;
    private List<Todo> todoList = new ArrayList<>();
    private boolean selectAll;

    @Override
    public void afterPropertiesSet() throws Exception {
        todoList = todoRepository.findAll();
    }

    public void addNewTask(){
        if(newTask.isEmpty())
            return;
        Todo todo = new Todo(newTask, false);
        todoRepository.save(todo);
        todoList = todoRepository.findAll();
        newTask = "";
    }

    public void removeTask(Todo todo){
        todoRepository.delete(todo);
        todoList = todoRepository.findAll();
    }

    public void updateTask(AjaxBehaviorEvent event){
        Long todoId = (Long) event.getComponent().getAttributes().get("todoId");
        Todo todo = todoList.stream().filter(t -> t.getId().equals(todoId)).findFirst().get();
        todoRepository.save(todo);
        todoList = todoRepository.findAll();
    }

    public void selectAll(AjaxBehaviorEvent event){
        for(Todo todo: todoList){
            todo.setCompleted(isSelectAll());
        }
        todoRepository.save(todoList);
    }

    public boolean getHasCompletedTodos(){
        return todoList.stream().anyMatch(Todo::getCompleted);
    }

    public void setFilter(final String filter){
        todoList = todoRepository.findAll();
        todoList = todoList.stream().filter(t -> {
            if ("All".equals(filter)) {
                return true;
            } else if ("Active".equals(filter)) {
                return !t.getCompleted();
            } else if ("Completed".equals(filter)) {
                return t.getCompleted();
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }

    public void clearCompleted(){
        todoRepository.delete(todoList.stream().filter(Todo::getCompleted).collect(Collectors.toList()));
        todoList = todoList.stream().filter(t->!t.getCompleted()).collect(Collectors.toList());
    }

    public String getNewTask() {
        return newTask;
    }

    public void setNewTask(String newTask) {
        this.newTask = newTask;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }
}
