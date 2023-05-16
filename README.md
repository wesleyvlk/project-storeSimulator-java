# storeSimulator

Projeto storeSimulator - um projeto de backend simples de e-commerce com Spring. O projeto utiliza o framework 
Spring com o Banco em memória H2, para criar endpoints RESTful para gerenciar usuários, produtos e vendas. O objetivo é 
fornecer um 
simulador de sistema simples e escalável para gerenciar uma loja virtual.

O projeto foi reestruturado para implementar uma arquitetura de subdomínios, dividindo as responsabilidades de forma mais clara e modular. A seguir está a estrutura do projeto com as camadas MVC:

- **Customer Subdomain**: Responsável pelo gerenciamento de clientes.
    - Controller: `CustomerController` que contém os endpoints CRUD para clientes. Endpoint: `"/customers"`.
    - Service: `CustomerService` que implementa a lógica de negócio para clientes.
    - Repository: `CustomerRepository` para acesso aos dados dos clientes.

- **Product Subdomain**: Responsável pelo gerenciamento de produtos.
    - Controller: `ProductController` que contém os endpoints CRUD para produtos. Endpoint: `"/products"`.
    - Service: `ProductService` que implementa a lógica de negócio para produtos.
    - Repository: `ProductRepository` para acesso aos dados dos produtos.

- **Sale Subdomain**: Responsável pelo gerenciamento de vendas e carrinhos de compras.
    - Controller: `SaleController` que contém os endpoints CRUD para vendas e carrinhos de compras. Endpoint: `"/sales-carts"`.
    - Service: `SaleService` que implementa a lógica de negócio para vendas e carrinhos de compras.
    - Repository: `SaleRepository` para acesso aos dados das vendas e carrinhos de compras.

- **Sale.ItemSale Subdomain**: Subdomínio filho responsável pelo gerenciamento dos itens de venda.
    - Controller: `SaleController` usando o mesmo controller do pai `Sale` contém os endpoints CRUD para itens de 
      venda. Endpoint: `"/sales-carts/items"`.
    - Service: `ItemSaleService` que implementa a lógica de negócio para itens de venda.
    - Repository: `ItemSaleRepository` para acesso aos dados dos itens de venda.

O projeto também está documentado com o Swagger, uma ferramenta para documentação de APIs. Depois de executar o 
projeto, a documentação pode ser acessada no seguinte endereço: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Executando o projeto

Para executar o projeto, você precisará ter o Java 17 (ou superior) instalado em sua máquina. Siga as etapas abaixo:

1. Faça o download do arquivo .jar da versão desejada na seção de releases do repositório.
2. Abra o terminal e navegue até o diretório onde o arquivo .jar está localizado.
3. Execute o seguinte comando: `java -jar store-simulator-0.0.1.jar`

Isso iniciará o servidor e o projeto estará disponível na porta 8080. Agora você pode acessar os endpoints e testar a API.
