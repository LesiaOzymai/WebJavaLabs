package com.example.spacecatsmarket.config;

import com.example.spacecatsmarket.repository.iml.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = "com.example.spacecatsmarket.repository",
    repositoryBaseClass = NaturalIdRepositoryImpl.class)
public class JpaRepositoryConfiguration {
}
