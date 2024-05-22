package com.mangazo.ecommerce.config;

import com.mangazo.ecommerce.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    private final EntityManager entityManager;

    @Autowired
    public DataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST and DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        // disable HTTP methods for Product: PUT, POST and DELETE
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        // disable HTTP methods for Region: PUT, POST and DELETE
        disableHttpMethods(Region.class, config, theUnsupportedActions);

        // disable HTTP methods for Comuna: PUT, POST and DELETE
        disableHttpMethods(Comuna.class, config, theUnsupportedActions);    

        // disable HTTP methods for Order: PUT, POST and DELETE
        disableHttpMethods(Order.class, config, theUnsupportedActions);

        // call an internal helper method
        exposeIds(config);
        // config.exposeIdsFor(Product.class);
        // config.exposeIdsFor(Product.class);
        // config.exposeIdsFor(ProductCategory.class);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);

    }

    private static void disableHttpMethods(Class<?> classToDisable, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(classToDisable)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // expose entity ids
        /*
        entityManager.getMetamodel().getEntities().forEach(
                entityType -> config.exposeIdsFor(entityType.getJavaType())
        );*/

        // get a list of all entity classes from the entity manager
        Set<EntityType<?>> entityTypeSet = entityManager.getMetamodel().getEntities();

        // create an array of the entity types
        List<Class<?>> entityClasses = new ArrayList<>();

        // get the entity types for the entities
        // entityTypeSet.forEach(entityType -> entityClasses.add(entityType.getJavaType()));
        for (EntityType<?> entityType : entityTypeSet) {
            entityClasses.add(entityType.getJavaType());
        }

        // expose the entity ids for the array of entity/domain types
        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
