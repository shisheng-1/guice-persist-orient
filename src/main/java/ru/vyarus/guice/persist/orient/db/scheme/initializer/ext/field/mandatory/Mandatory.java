package ru.vyarus.guice.persist.orient.db.scheme.initializer.ext.field.mandatory;

import ru.vyarus.guice.persist.orient.db.scheme.initializer.core.spi.field.SchemeFieldInit;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Scheme model field extension to mark property as mandatory.
 * <p>Sample sql: alter property Model.name mandatory true</p>
 *
 * @author Vyacheslav Rusakov
 * @since 08.03.2015
 */
@Target(FIELD)
@Retention(RUNTIME)
@SchemeFieldInit(MandatoryPropertyExtension.class)
public @interface Mandatory {

    /**
     * @return true to make property mandatory, false to unset mandatory
     */
    boolean value() default true;
}
