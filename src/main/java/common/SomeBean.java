package common;

import annotations.AutoInjectable;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;
    @AutoInjectable
    private SomeOtherInterface field2;

    public SomeBean() {
    }

    public int foo1(){
        return field1.doSomething();
    }
    public int foo2(){
        return field2.doSomeOther();
    }

}
