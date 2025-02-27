
### 2.1
#### f)
```O teste atual não cobre casos como preços ou quantidades negativas, o que poderia causar comportamentos inesperados. Também não trata topN zero ou negativo, que deveria retornar uma lista vazia ou lançar uma exceção. Além disso, não há verificação para valores extremamente altos que poderiam causar problemas de overflow ou perda de precisão. Esses casos garantiriam uma implementação mais robusta.```

### 2.2
#### b)
```
Cria-se um mock do ISimpleHttpClient com o Mockito para evitar chamadas reais à API. Em vez disso, define-se uma resposta JSON fixa com when(...).thenReturn(...), garantindo que sempre que doHttpGet(url) for chamado, ele retorna esse JSON.

Depois, injeta-se esse mock no ProductFinderService, assim ele não usa um cliente HTTP real. No teste, chama-se findProductDetails(id) e verifica-se se ele cria um objeto Product certinho, com os atributos esperados.
```