package clientFiles;

import java.io.Serializable;

public class SimpleCalc implements Serializable {

    public SimpleCalc() { }

    public int add(int a, int b) {
        return a+b;
    }

    public int substract(int a, int b) {
        return a - b;
    }
}
