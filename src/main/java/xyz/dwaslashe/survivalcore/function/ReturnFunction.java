package xyz.dwaslashe.survivalcore.function;

public class ReturnFunction {

    private final boolean value;

    public ReturnFunction(boolean value, Runnable runnable){
        this.value = value;
        if(value) runnable.run();
    }

    public void orElse(Runnable runnable){
        if(!value) runnable.run();
    }

    public boolean isValue() {
        return value;
    }
}
