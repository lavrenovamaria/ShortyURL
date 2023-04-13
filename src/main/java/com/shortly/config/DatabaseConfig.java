package com.shortly.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
@Configuration
@EnableJpaRepositories(basePackages = "com.shortly.repository")
@EntityScan(basePackages = "com.shortly.domain")
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}

/**
 * Конфигурация Spring Boot
 * @Configuration: Эта аннотация указывает, что класс
 * является источником определений bean-компонентов,
 * которые должны быть обработаны Spring контейнером.
 * Она является альтернативой использованию XML-файлов
 * для конфигурации Spring. Классы с аннотацией
 * @Configuration обычно содержат методы, аннотированные
 * с @Bean, которые определяют фабричные методы для
 * создания компонентов приложения.
 */

/**
 *  @EnableJpaRepositories: Эта аннотация активирует
 *  создание Spring Data JPA репозиториев.
 *  Вам необходимо указать basePackages для определения
 *  пакета, в котором находятся интерфейсы репозиториев.
 *  Spring автоматически создаст реализации этих
 *  репозиториев во время выполнения.
 */

/**
 *  @EntityScan: Эта аннотация указывает Spring, где
 *  искать сущности JPA (классы, аннотированные с @Entity).
 *  basePackages определяет пакеты, которые следует
 *  сканировать в поисках сущностей. Это нужно для
 *  автоматической настройки и интеграции сущностей
 *  JPA с вашим приложением.
 */

/**
 *  @Primary: Эта аннотация указывает, что определенный
 *  bean-компонент должен иметь высший приоритет при
 *  выборе между несколькими кандидатами, которые могут
 *  быть автосвязаны с одним и тем же типом. Это полезно,
 *  когда у вас есть несколько реализаций одного и того
 *  же интерфейса, и вы хотите указать, какой из них
 *  следует использовать по умолчанию.
 */

/**
 *  @ConfigurationProperties: Эта аннотация связывает
 *  свойства конфигурации с полями объекта. В вашем
 *  случае prefix = "spring.datasource" указывает на
 *  то, что свойства, начинающиеся с spring.datasource,
 *  должны быть связаны с полями в этом классе.
 *  Это позволяет вам легко настроить параметры конфигурации
 *  через файлы application.properties или application.yml.
 */