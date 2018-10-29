//package com.example.joanderson.calculadora.treePackage;

/**
 * Created by joanderson on 12/08/18.
 */

public abstract class Node{

    private Node father;
    private Node left;
    private Node right;

    public void setFather(Node father){
        this.father=father;
    }
    public Node getFather(){
        return this.father;
    }
    public void setLeft(Node left){
        this.left=left;
    }
    public void setRight(Node right){
        this.right=right;
    }
    public Node getLeft(){
        return this.left;
    }
    public Node getRight(){
        return this.right;
    }

}
