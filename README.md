A arquitetura **Clean** + **MVVM** (Model-View-ViewModel) é uma abordagem eficaz para separar responsabilidades e organizar o código de maneira modular. Vamos entender como ela funciona e se relaciona com os princípios **SOLID**, usando um exemplo de um projeto de login com consumo de API e gerenciamento de estados.

### Arquitetura Clean + MVVM

A arquitetura é dividida em três camadas principais:

1. **Data Layer**:
   - Responsável pelo consumo de dados externos, como APIs. No exemplo, a camada de dados contém o `ApiService` que faz a comunicação com a API e os modelos de requisição e resposta (por exemplo, `LoginRequestModel`, `LoginResponseModel`).
   - Os **Repositórios** implementam a lógica para buscar e manipular dados, como o `LoginRepositoryImpl`, que usa o `ApiService` para fazer as chamadas de login e retornar os dados no formato esperado.
   
2. **Domain Layer**:
   - Contém as **entidades** do domínio (como `LoginResponseEntity`, `UserResponseEntity`) e os **casos de uso** (como `LoginUseCase` e `GetUserUseCase`). 
   - Os casos de uso encapsulam a lógica de negócio e se comunicam com os repositórios definidos na camada de dados. 
   - O **contrato de repositório** é definido aqui, especificando o que cada repositório precisa fazer, sem especificar como. Isso segue o princípio de **Open-Closed Principle** (OCP), já que os contratos de repositórios podem ser estendidos sem modificar seu código existente.

3. **Presenter (ViewModel)**:
   - No contexto do **MVVM**, o ViewModel contém a lógica de apresentação e se comunica com a camada de **Domain** através dos casos de uso.
   - A camada de apresentação é responsável por gerenciar os estados da UI, como `LoginState` e `UserState`, e os **ViewModels** (como `LoginViewModel`) expõem esses estados para a **View** (no caso, a interface de usuário com Jetpack Compose).
   - O **ViewModel** é ligado diretamente à UI e é responsável por reagir às mudanças de estado e invocar os casos de uso.

### SOLID

Agora, vamos ver como a arquitetura segue os princípios **SOLID**:

1. **Single Responsibility Principle (SRP)**:
   - Cada classe tem uma única responsabilidade. Por exemplo, a responsabilidade de fazer login é isolada no **`LoginUseCase`**, enquanto o gerenciamento de estados de login é feito pelo **`LoginViewModel`**. Dessa forma, cada classe tem uma função clara e separada.

2. **Open-Closed Principle (OCP)**:
   - As interfaces de repositórios (por exemplo, `LoginRepository`) estão abertas para extensão, mas fechadas para modificação. Se precisarmos adicionar um novo método ao repositório (como a função `getUser`), podemos fazer isso sem alterar o código existente, apenas implementando um novo repositório.

3. **Liskov Substitution Principle (LSP)**:
   - Este princípio é usado, por exemplo, nos testes de unidade. Durante os testes, podemos substituir implementações concretas de repositórios por **mock objects** (como `FakeLoginRepository`), sem afetar o comportamento do código. Isso permite que o código seja substituível por outros tipos sem violar a lógica esperada.

4. **Interface Segregation Principle (ISP)**:
   - As interfaces são segregadas para evitar que classes implementem métodos que não utilizam. Por exemplo, no repositório, separa-se os métodos de login e de obtenção de usuário em interfaces distintas, assim, classes que precisam apenas de uma funcionalidade específica não são forçadas a implementar a outra.

5. **Dependency Inversion Principle (DIP)**:
   - A inversão de dependência é alcançada através da **injeção de dependência**. O **`LoginViewModel`**, por exemplo, não cria instâncias de `LoginUseCase` ou `GetUserUseCase`, mas os recebe através do construtor. Isso facilita o teste e a manutenção, já que as dependências podem ser trocadas facilmente sem afetar o código cliente.

### Testes

Testes de unidade e de interface são essenciais para garantir que a arquitetura funcione conforme esperado. No caso do código apresentado:

- **Testes de unidade**: Os testes para os **repositórios** e **casos de uso** garantem que a lógica de negócio funcione corretamente. Utilizando mocks para a API, podemos testar diferentes cenários, como sucesso ou falha na requisição.
  
- **Testes de interface**: Com o uso do **Jetpack Compose**, é possível testar os comportamentos da UI diretamente, como a exibição de campos de entrada e as transições de estado (por exemplo, de loading para sucesso ou erro) usando o **`composeTestRule`**.

### Conclusão

Essa arquitetura, seguindo os princípios SOLID, oferece uma base sólida, modular e facilmente testável para desenvolver aplicações escaláveis. A separação de responsabilidades (SRP), a possibilidade de estender funcionalidades sem modificar código existente (OCP), e a inversão de dependência (DIP) permitem uma manutenção mais fácil e maior flexibilidade para evoluir o sistema ao longo do tempo.
