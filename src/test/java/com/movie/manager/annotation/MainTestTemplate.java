package com.movie.manager.annotation;

import com.movie.manager.MovieManagerApplication;
import com.movie.manager.config.db.PostgresSqlContainerConfig;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {
        MovieManagerApplication.class,
        PostgresSqlContainerConfig.class,
})
public @interface MainTestTemplate {
}
