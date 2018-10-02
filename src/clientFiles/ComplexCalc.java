package clientFiles;

import java.io.Serializable;

public class ComplexCalc implements Serializable {

    public ComplexCalc() { }

    public int multiply(int a, int b) {
        return a * b ;
    }

    public int divide(int a, int b) {
        return a / b;
    }
}
