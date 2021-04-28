package annotations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;
/**
 * Класс - инициализацией полей доступными реализациями
 * @author Denis Popov
 * @version 1.0
 */
public class Injector {
    private Properties properties;
    /**
     *  Конструктор подключает файл ресурсов
     */
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
    /**
     *  Метод заполнения полей
     * @param val - принимает элемент, который представляет исходный обьект
     * @return - возвращает объект с иниуциализированными полями
     */
    public <T> T inject(T val) throws Exception {
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
        return val;
    }
    /**
     *  Метод обработки полей
     * @param fil - поле исходного обьекта
     * @param stratclass - исходный объект
     */
    private<T> void getProperty(Field fil, T stratclass ) throws Exception{
        Class<?> clazz =getClassFromFile(fil.getType());
        if (clazz==null){
            throw new Exception("Невозможно обработать "+fil.getName());
        }
        fil.setAccessible(true);
        fil.set(stratclass, clazz.getConstructor().newInstance());

    }
    /**
     *  Метод получение класса инициализации из файла
     * @param val - класс который нужно найти
     */
    private <T> Class<?> getClassFromFile(Class<T> val) throws Exception{
        String className=properties.getProperty(val.getSimpleName());
        if(className==null){
            return  null;
        }
        return this.getClass().getClassLoader().loadClass(className);
    }
}
