# Menu Foods 1.0.0

API de uma plataforma online para restaurantes. Nela é possível criar usuários, cujo são distribuidos em três tipos, um usuário comum, administrador e master. Os usuários comuns são os clientes que estão consultando o cardápio (feature de efetuação de compra estará disponível em versões futuras), já os administradores e masters são quem gerenciam a aplicação,
podendo principalmente, utilizar a funcionalidade de inserção e remoção de itens no cardápio.

#### Tecnologias Utilizadas
- Java 20
- Spring Boot 3.1.1
- Spring Security
- JWT Authentication
- JPA/Hibernate
- H2 Database (Dev environment)
- PostgreSQL in AWS-RDS (Prod environment)
- Bucket in AWS-S3
- Open API (Swagger)

## Diagrama UML das entidades em Mermaid Framework

``` mermaid 
classDiagram
  class User {
    + id: Long
    + name: String
    + email: String
    + login: String
    + password: String
    + photoProfileUrl: String
    + role: UserRole
    + createadAt: Date
    + updateAt: Date
  }

  class MenuItem {
    + id: Long
    + user: User
    + name: String
    + description: String
    + type: MenuItemType
    + photoUrl: String
    + ingredients: List<Ingredient>
  }

  class Ingredient {
    + id: Long
    + name: String
    + menuItems: List<MenuItem>
  }

  User "1" *-- "0..*" MenuItem
  MenuItem "1" *-- "0..*" Ingredient
```