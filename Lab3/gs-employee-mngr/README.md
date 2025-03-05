3.1 a)
```
    @Test
     void givenEmployees_whenGetEmployees_thenStatus200()  {
        (...)
        assertThat(response.getBody()).extracting(Employee::getName)
                              .containsExactly("bob", "alex");

    }
    
```

b)
```
@AutoConfigureTestDatabase
@ExtendWith

```

c)
```@Test
void whenSearchValidName_thenEmployeeShouldBeFound() {
    String name = "alex";
    Employee found = employeeService.getEmployeeByName(name);
    assertThat(found.getName()).isEqualTo(name);
}

```
d)
```
@Mock é usado em testes unitários com Mockito, sem envolvimento do Spring Context.

@MockBean é usado em testes de integração com Spring Boot, onde o mock é integrado ao contexto do Spring, substituindo beans reais.

```


e)
```
O ficheiro application-integrationtest.properties é essencial para configurar um ambiente específico para testes de integração, permitindo que esses testes sejam executados de forma isolada e controlada, sem interferir com as configurações de produção ou desenvolvimento.
```

