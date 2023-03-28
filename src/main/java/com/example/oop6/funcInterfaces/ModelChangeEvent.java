package com.example.oop6.funcInterfaces;

@FunctionalInterface
public interface ModelChangeEvent {
    public void modelChange(int width, int height);
}
