package com.example.oop6.utils.mvc;

import com.example.oop6.models.field.commands.Command;

import java.util.Stack;
import java.util.function.Consumer;

public class StackOperation {
    private Stack<Command> commands;
    private Consumer<Stack<Command>> handler;

    public StackOperation() {
        commands = new Stack<>();
    }

    public void push(Command command){
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
