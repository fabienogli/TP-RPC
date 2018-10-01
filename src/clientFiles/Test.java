package clientFiles;

import java.io.Serializable;

public class Test implements Serializable {

    public Test() {

    }

    public int add(int a, int b) {
        return a+b;
    }

    public int minus(int a, int b) {
        return a - b;
    }
}
