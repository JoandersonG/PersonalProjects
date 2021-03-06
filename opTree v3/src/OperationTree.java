//package com.example.joanderson.calculadora.treePackage;

import java.util.Stack;

public class OperationTree {

    private Tree tree;
    //private Node root;
    private OpNode lastOpNode;
    private Node lastNumNode;
    private Node lastAddedNode;
    private boolean isParen = false;
    //private Stack<Node> rootStack;

    public OperationTree() {
        tree = new Tree();
   //     rootStack = new Stack<>();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public void addNode(double key) {
        NumNode node = new NumNode(key);
        if(isParen) {
            //tou num parênteses
            ParenNode pn = (ParenNode) lastAddedNode;
            pn.addNo(key);
            lastAddedNode = pn;
            return;
        }
        if (isEmpty()) {
            tree.setRoot(node);
        }
        else{
            lastOpNode.setRight(node);
            node.setFather(lastOpNode);
        }
        lastAddedNode = node;
        lastNumNode = node;
        tree.addSize();
    }

    public void addNode(char key) {

        if (key == '(') {
            ParenNode node = new ParenNode();

            if (isEmpty()) {
                tree.setRoot(node);
            }
            else if (lastOpNode == null) {
                //árvore só tem uma raiz que é parênteses
                ParenNode parenNode = (ParenNode) lastAddedNode;
                parenNode.addNo('(');
            }
            else {
                lastOpNode.setRight(node);
                node.setFather(lastOpNode);
            }
            isParen = true;
            lastAddedNode = node;
            lastNumNode = node;
            return;
        }

        if (key == ')') {
            ParenNode pn = (ParenNode) lastAddedNode;
            if (pn.isParen()) {
                //parênteses dentro do parênteses
                pn.addNo(')');
            }
            else {
                isParen = false;
            }
            return;
        }

        if (isParen) {
            ParenNode pn = (ParenNode) lastAddedNode;
            pn.addNo(key);
            return;
        }

        OpNode node = new OpNode(key);

        //if (key == '+' || key == '-' || lastOpNode == null || (lastOpNode.getValue() == '*' || lastOpNode.getValue() == '/' )) {
        if (key == '+' || key == '-' || lastOpNode == null) {
            //se torna raiz
            node.setLeft(tree.getRoot());
            tree.getRoot().setFather(node);
            tree.setRoot(node);
        }
        else if (lastNumNode == tree.getRoot()) {
            //é '*' ou '/'
            //só tem um número que é raiz
            node.setLeft(tree.getRoot());
            tree.getRoot().setFather(node);
            tree.setRoot(node);
        }
	    else {
            lastNumNode.getFather().setRight(node);
            node.setFather(lastNumNode.getFather());
            lastNumNode.setFather(node);
            node.setLeft(lastNumNode);
        }

        lastAddedNode = node;
        lastOpNode = node;
        tree.addSize();
    }

    public void removeLastAddedNode() {

        if (lastAddedNode.getClass() == ParenNode.class && isParen) {
            // é dentro parênteses
            ParenNode pn = (ParenNode) lastAddedNode;

            if (pn.isEmpty()) {
                // é abertura de parênteses
                lastAddedNode = lastAddedNode.getFather();
                if (lastAddedNode != null) {
                    lastAddedNode.setRight(null);
                    lastNumNode = lastAddedNode.getLeft();
                    if (lastNumNode != null) {
                        while (lastNumNode.getRight()!= null) {
                            lastNumNode = lastNumNode.getRight();
                        }
                    }

                }
                else {
                    lastNumNode =null;
                }
                isParen = false;
            }
            else {
                // tenho que remover um elemento dentro da árvore do parênteses
                pn.removerUltimoNo();
            }


            /*

            isParen = false;
            lastAddedNode = lastAddedNode.getFather();
            if (lastAddedNode != null) {
                lastAddedNode.setRight(null);
                lastNumNode = lastAddedNode.getLeft();
            }
            else {
                lastNumNode = lastAddedNode;
            }
            if (lastNumNode != null) {
                while (lastNumNode.getRight()!= null) {
                    lastNumNode = lastNumNode.getRight();
                }
            }
            */
        }

        else if (lastAddedNode.getClass() == ParenNode.class && !isParen) {
            //é feixo do parênteses
            isParen = true;
        }

        else if (lastAddedNode.getClass() == NumNode.class) {
            //é número
            lastAddedNode = lastAddedNode.getFather();
            if (lastAddedNode != null) {
                lastAddedNode.setRight(null);
                lastNumNode = lastAddedNode.getLeft();
                if (lastNumNode != null) {
                    while (lastNumNode.getRight()!= null) {
                        lastNumNode = lastNumNode.getRight();
                    }
                }

            }
            else {
                lastNumNode =null;
            }
        }

        else if (lastAddedNode == lastOpNode && (lastOpNode.getValue() == '+' || lastOpNode.getValue() == '-')) {
            // é mais ou menos
            tree.setRoot(lastAddedNode.getLeft());
            if (lastAddedNode.getLeft().getClass() == OpNode.class) {
                lastOpNode = (OpNode) lastAddedNode.getLeft();
            }
            else {
                lastOpNode = null;
            }
            lastAddedNode.getLeft().setFather(null);
            //Node aux = lastAddedNode.getLeft();
//            while (aux != null && aux.getRight() != null) {
//                aux = aux.getRight();
//            }
            lastAddedNode = lastNumNode;
        }

        else {
            //é vezes ou dividido
            lastAddedNode.getLeft().setFather(lastAddedNode.getFather());
            if (lastAddedNode.getFather() != null) {
                if (lastAddedNode.getFather().getLeft() == lastAddedNode) {
                    lastAddedNode.getFather().setLeft(lastAddedNode.getLeft());
                } else {
                    lastAddedNode.getFather().setRight(lastAddedNode.getLeft());
                }
            }
            else {
                tree.setRoot(lastAddedNode.getLeft());
            }
            lastOpNode = (OpNode) lastAddedNode.getFather();
            lastAddedNode = lastAddedNode.getLeft();
        }





/*
        if (lastAddedNode == lastNumNode) {
            //tenho que remover um número
            if(lastNumNode != tree.getRoot()) {
                lastNumNode.getFather().setRight(null);
            }
            else{
                tree.setRoot(null);
            }
        }
        else {
            //tenho que remover um Operador

            if (lastOpNode == tree.getRoot()) {
                //é a raiz
                tree.setRoot(tree.getRoot().getLeft());
            }
            else {
                //é um '*' ou '/' pelo meio da árvore
                lastOpNode.getFather().setRight(lastOpNode.getLeft());
                lastOpNode.getLeft().setFather(lastOpNode.getFather());
            }

        }
        tree.subSize();
        */
    }

    public boolean isIsParen() {
        return isParen;
    }

    public double calculateTree() {
        return calculateTree(tree.getRoot());
    }

    private double calculateTree(Node root) {
        if (root == null) {
            return 0;
        }
        if(root.getClass() == OpNode.class) {
            OpNode opn =  (OpNode) root;
            return calculateTree(opn);
        }
        else if(root.getClass() == NumNode.class){
            NumNode nn =  (NumNode) root;
            return calculateTree(nn);
        }
        else {
            //é parênteses
            ParenNode pn = (ParenNode) root;
            return pn.resultado();
        }
    }

    private double calculateTree(NumNode root) {
        if (root == null) {
            return 0;
        }
        return root.getValue();
    }

    private double calculateTree(OpNode root) {
        if (root == null) {
            return 0;
        }

        switch (root.getValue()) {
            case '+':
                return calculateTree(root.getLeft()) + calculateTree(root.getRight());
            case '-':
                return calculateTree(root.getLeft()) - calculateTree(root.getRight());
            case '*':
                return calculateTree(root.getLeft()) * calculateTree(root.getRight());
            case '/':
                return calculateTree(root.getLeft()) / calculateTree(root.getRight());
            default:
                return 0;
        }
    }

}

