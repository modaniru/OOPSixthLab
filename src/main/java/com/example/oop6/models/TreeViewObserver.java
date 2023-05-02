package com.example.oop6.models;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class TreeViewObserver implements Observer {
    private TreeView<String> tree;
    private List<TreeItem<String>>  selectedShapes = new ArrayList<>();

    public TreeViewObserver(TreeView<String> tree) {
        this.tree = tree;
    }

    @Override
    public void notifyObserver(Container<Shape> shapes) {
        selectedShapes = new ArrayList<>();
        TreeItem<String> root = new TreeItem<>("root");
        for (Shape shape : shapes) {
            root.getChildren().add(getTreeItem(shape));
        }
        tree.setRoot(root);
        MultipleSelectionModel<TreeItem<String>> msm = tree.getSelectionModel();
        for (TreeItem<String> s : selectedShapes) {
            msm.select(s);
        }
        tree.setShowRoot(false);
    }

    private TreeItem<String> getTreeItem(Shape shape) {
        Shape instance = shape.getInstance();

        String className = instance.getClass().getSimpleName();
        ImageView image = new ImageView(Images.valueOf(className.toUpperCase()).getImage());
        image.setFitWidth(14);
        image.setFitHeight(14);
        TreeItem<String> treeItem = new TreeItem<>(className, image);
        if(shape != instance){
            selectedShapes.add(treeItem);
        }
        for (Shape s : shape.getShapes()) {
            TreeItem<String> ti = getTreeItem(s);
            treeItem.getChildren().add(ti);
        }
        return treeItem;
    }
}
