package com.example.oop6.models;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class TreeViewObserver implements Observer {
    private TreeView<String> tree;

    public TreeViewObserver(TreeView<String> tree) {
        this.tree = tree;
    }

    @Override
    public void notifyObserver(Container<Shape> shapes) {
        TreeItem<String> root = new TreeItem<>("root");
        for (Shape shape : shapes) {
            root.getChildren().add(getTreeItem(shape));
        }
        tree.setRoot(root);
        tree.setShowRoot(false);
    }

    private TreeItem<String> getTreeItem(Shape shape) {
        Shape instance = shape.getInstance();

        String className = instance.getClass().getSimpleName();
        ImageView image = new ImageView(Images.valueOf(className.toUpperCase()).getImage());
        if(shape != instance){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(1);
            image.setEffect(colorAdjust);
        }
        image.setFitWidth(14);
        image.setFitHeight(14);
        TreeItem<String> treeItem = new TreeItem<>(className, image);
        for (Shape s : shape.getShapes()) {
            TreeItem<String> ti = getTreeItem(s);
            treeItem.getChildren().add(ti);
        }
        return treeItem;
    }
}
