package com.example.oop6.utils.mvc;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.Command;

import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;

/* Обертка над стеком, с обработчиком ее изменения */
public class StackOperation {
    private final Stack<Command> commands;
    private Consumer<Stack<Command>> handler;

    public StackOperation() {
        commands = new Stack<>();
    }

    public void push(Command command) {
        if (commands.size() > 32) commands.remove(0);
        commands.push(command);
        handler.accept(commands);
    }

    public Optional<Command> popUnExecute() {
        Optional<Command> op = Optional.empty();
        if (!commands.isEmpty()) {
            op = Optional.of(commands.pop());
            op.get().unExecute();
            handler.accept(commands);
        }
        return op;
    }

    public Optional<Command> popExecute(PaintField paintField) {
        Optional<Command> op = Optional.empty();
        if (!commands.isEmpty()) {
            op = Optional.of(commands.pop());
            op.get().execute(paintField);
            handler.accept(commands);
        }
        return op;
    }

    public void clear() {
        commands.clear();
        handler.accept(commands);
    }

    public void setModelChangeEvent(Consumer<Stack<Command>> consumer) {
        this.handler = consumer;
    }
}
