package br.gov.bomdestino.apigateway;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.gov.bomdestino.apigateway");

        noClasses()
            .that()
                .resideInAnyPackage("br.gov.bomdestino.apigateway.service..")
            .or()
                .resideInAnyPackage("br.gov.bomdestino.apigateway.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..br.gov.bomdestino.apigateway.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
