import annotations.Injector;
import common.EmptyClass;
import common.SomeBean;
import common.SomeBeanExp;

import static org.junit.jupiter.api.Assertions.*;

public class InjectTest {
    @org.junit.jupiter.api.Test
    void calcIntegers() {

        try{
            SomeBean sb = (new Injector()).inject(new SomeBean());
            assertTrue(2==sb.foo1());
            assertTrue(5==sb.foo2());

        }
        catch (Exception ex){
            ex.getMessage();
        }
    }
    @org.junit.jupiter.api.Test
    void calcExeptions() {
        try{
            SomeBeanExp sb = (new Injector()).inject(new SomeBeanExp());

        }catch (Exception ex){
            assertEquals("Невозможно обработать field1", ex.getMessage());
        }


        try{
            EmptyClass sb = (new Injector()).inject(new EmptyClass());
        }
        catch (Exception ex){
            assertEquals("В классе нету полей", ex.getMessage());
        }
    }
}
