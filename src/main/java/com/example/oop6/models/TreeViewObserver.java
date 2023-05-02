package com.example.oop6.models;

import com.example.oop6.models.shapes.Rectangle;
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
    private TreeView<Shape> tree;
    private List<TreeItem<Shape>>  selectedShapes = new ArrayList<>();
    private boolean isNotified = false;

    public boolean isNotified() {
        return isNotified;
    }

    public TreeViewObserver(TreeView<Shape> tree) {
        this.tree = tree;
    }

    @Override
    public void notifyObserver(Container<Shape> shapes) {
        isNotified = true;
        selectedShapes = new ArrayList<>();
        TreeItem<Shape> root = new TreeItem<>(new Rectangle());
        for (Shape shape : shapes) {
            root.getChildren().add(getTreeItem(shape));
        }
        tree.setRoot(root);
        MultipleSelectionModel<TreeItem<Shape>> msm = tree.getSelectionModel();
        for (TreeItem<Shape> s : selectedShapes) {
            msm.select(s);
        }
        tree.setShowRoot(false);
        isNotified = false;
    }

    private TreeItem<Shape> getTreeItem(Shape shape) {
        Shape instance = shape.getInstance();
        String className = instance.getClass().getSimpleName();
        ImageView image = new ImageView(Images.valueOf(className.toUpperCase()).getImage());
        image.setFitWidth(14);
        image.setFitHeight(14);
        TreeItem<Shape> treeItem = new TreeItem<>(shape, image);
        //Крайний случай рекурсии
        if(shape != instance){
            selectedShapes.add(treeItem);
        }
        for (Shape s : instance.getShapes()) {
            TreeItem<Shape> ti = getTreeItem(s);
            treeItem.getChildren().add(ti);
        }
        treeItem.setExpanded(true);
        return treeItem;
    }
}
