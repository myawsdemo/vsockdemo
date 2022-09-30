package jniexamples.helloworld;

public class HelloWorld {

    static {
        System.loadLibrary("helloworld");
    }

    public static void main(String[] args) {
        helloJava();
        helloNative();
    }
    
    public static void helloJava() {
        System.out.println("Hello world from Java!");
    }

    public static native void helloNative();
}