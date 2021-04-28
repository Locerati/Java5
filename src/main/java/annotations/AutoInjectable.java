package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //Указывает, что наша Аннотация может быть использована во время выполнения через Reflection.
@Target(ElementType.FIELD) //Указывает, что целью нашей Аннотации является поле.
public @interface AutoInjectable {
}
