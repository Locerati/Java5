package annotations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

public class Injector {
    private Properties properties;
    public Injector() throws IOException {
        properties = new Properties();
        String propFileName="data.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if(inputStream!=null){
            properties.load(inputStream);
        }
        else
        {
            throw new FileNotFoundException("property file"+propFileName+"not found in the classpath");
        }
    }

    public <T> void inject(T val) throws Exception {
        Class c = val.getClass();
        Field[] fs = c.getDeclaredFields(); // получили массив с объектами Field, соответствующие полям класс
        if (fs.length ==0){
            throw new Exception("В классе нету полей");
        }
        for (Field fil : fs)
        {
            if (fil.isAnnotationPresent(AutoInjectable.class)){
                getProperty(fil,val);
            }

        }
    }
    private<T> void getProperty(Field fil, T stratclass ) throws Exception{
        Class<?> clazz =getClassFromFile(fil.getType());
        if (clazz==null){
            throw new Exception("Невозможно обработать"+fil.getName());
        }
        fil.setAccessible(true);
        fil.set(stratclass, clazz.getConstructor().newInstance());

    }
    private <T> Class<?> getClassFromFile(Class<T> val) throws Exception{
        String className=properties.getProperty(val.getSimpleName());
        if(className==null){
            return  null;
        }
        return this.getClass().getClassLoader().loadClass(className);
    }
}
