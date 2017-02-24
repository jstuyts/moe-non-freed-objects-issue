package com.squins.reproduction.nonfreedobjects.ui;

class SecondScreenFactory {



    fun createScreen():SecondScreen {

        return SecondScreen.alloc().init()

    }
}
