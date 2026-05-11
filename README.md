# 🍔️ Local Eats API

API RESTful para gestão de pedidos e cardápio de lanchonetes, desenvolvida para modernizar o comércio em Terra Nova - PE.

## 🚀️ Tecnologias
- **Java 21** (LTS)
- **Spring Boot 4.0.5**
- **Spring Data JPA**
- **MySQL**
- **Maven**

## 📋️ Sobre o Projeto

O **Local Eats** nasce da necessidade de organizar o fluxo de pedidos em lanchonetes locais. O foco é aplicar os conceitos de  **Programção Orientada a Objetos (POO)** aprendidos no curso da Loiane Groner e no IFSertãoPE para criar um sistema robusto e escalável.

## 📍 Roadmap de Desenvolvimento

### 🟢 Fase 1: Fundação e Core do Sistema (Concluído)
* [x] Modelagem do banco de dados MySQL
* [x] CRUD completo de **Usuários**, **Produtos** e **Endereços**
* [x] Sistema de **Pedidos** e itens vinculados
* [x] Implementação de Enums (**Status** e **Pagamento**)
* [x] Gerenciamento de **Taxas de Entrega**

### 🔒 Fase 2: Segurança e Controle de Acesso (Em Andamento)
* [x] Criptografia de senhas com **BCrypt**
* [x] Implementação de Perfis de Usuário (**ADMIN** e **CLIENT**)
* [x] Criação da camada **UserSpringSecurity** (UserDetails)
* [x] Tratamento global de exceções de banco de dados
* [ ] Implementação de **UserDetailsServiceImpl** (Busca no banco)

### 🟡 Fase 3: Autenticação e Autorização (Próximos Passos)
* [ ] Configuração de **Autenticação JWT** (Tokens)
* [ ] Filtros de segurança para proteger rotas da API
* [ ] Autorização baseada em Roles (Ex: Só ADMIN altera taxas)

### 🚀 Fase 4: Refinamentos e Documentação
* [ ] Documentação interativa com **Swagger (OpenAPI)**
* [ ] Preparação para o Deploy (Hospedagem da API)

## 👨‍💻️ Sobre o Autor
**Nereu Vítor Pereira Lima**
Estudante de sistemas para Internet - IFSertãoPE (Salgueiro)