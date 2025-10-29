package org.example;

import org.example.controller.MenuController;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        MenuController menuController = new MenuController();

        if (args.length == 1) {
            menuController.startWithFile(args[0]);
        } else {
            menuController.start();
        }
    }
}
