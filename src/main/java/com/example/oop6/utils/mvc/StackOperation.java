package com.example.oop6.utils.mvc;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;
import java.util.function.Consumer;

/* Обертка над стеком, с обработчиком ее изменения */
public class StackOperation {
    //private final Stack<Command> commands;
    private Consumer<ObservableList<Command>> handler;
    private final ObservableList<Command> commands = FXCollections.observableArrayList();

    public void push(Command command) {
        if (commands.size() > 25) commands.remove(0);
        commands.add(command);
        handler.accept(commands);
    }

    public Optional<Command> popUnExecute() {
        Optional<Command> op = Optional.empty();
        if (!commands.isEmpty()) {
            Command command = commands.get(commands.size() - 1);
            commands.remove(commands.size() - 1);
            op = Optional.of(command);
            op.get().unExecute();
            handler.accept(commands);
        }
        return op;
    }

    public Optional<Command> popExecute(PaintField paintField) {
        Optional<Command> op = Optional.empty();
        if (!commands.isEmpty()) {
            Command command = commands.get(commands.size() - 1);
            commands.remove(commands.size() - 1);
            op = Optional.of(command);
            op.get().execute(paintField);
            handler.accept(commands);
        }
        return op;
    }

    public void clear() {
        commands.clear();
        handler.accept(commands);
    }

    public ObservableList<Command> getCommands() {
        return commands;
    }

    public void setModelChangeEvent(Consumer<ObservableList<Command>> consumer) {
        this.handler = consumer;
    }
}
