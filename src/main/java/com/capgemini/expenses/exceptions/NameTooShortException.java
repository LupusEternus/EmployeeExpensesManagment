package com.capgemini.expenses.exceptions;

public class NameTooShortException extends Exception{

    public NameTooShortException(){
        super();
    }
    public NameTooShortException(String e){
        super(e);
    }
}
