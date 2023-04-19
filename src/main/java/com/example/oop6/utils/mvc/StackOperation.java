package com.example.oop6.utils.mvc;

import com.example.oop6.funcInterfaces.ModelChangeEvent;
import com.example.oop6.models.field.Command;

import java.util.Stack;
import java.util.function.Consumer;

public class StackOperation {
    private Stack<Command> commands;
    private Consumer<Stack<Command>> handler;

    public StackOperation() {
        commands = new Stack<>();
    }

    public void push(Command command){
        if(command == null) return;
        commands.push(command);
        handler.accept(commands);
    }
    public void pop(){
        if(!commands.isEmpty()) {
            commands.pop().unExecute();
            handler.accept(commands);
        }
    }

    public void clear(){
        commands.clear();
        handler.accept(commands);
    }

    public void setModelChangeEvent(Consumer<Stack<Command>> consumer) {
        this.handler = consumer;
    }
}
