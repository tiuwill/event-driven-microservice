# Arquitetura de Microservices com Event Storming e CQRS

Este repositório foi criado como apoio para a disciplina **Arquitetura de Microservices com Event Storming e CQRS**.  
O objetivo é auxiliar alunos e alunas a praticar os conceitos apresentados em aula e implementar, na prática, um projeto completo utilizando os padrões **CQRS** (Command Query Responsibility Segregation) e, em seguida, **Event Sourcing**.

## 🎯 Objetivos do Projeto

- Entender e aplicar a separação entre os fluxos de escrita (Command) e leitura (Query) com CQRS.
- Modelar domínios utilizando a técnica de **Event Storming**.
- Evoluir a arquitetura para um modelo de **Event Sourcing**, utilizando uma base de eventos como fonte da verdade.
- Utilizar ferramentas modernas para infraestrutura, mensageria e persistência.

## ✅ Pré-requisitos

Para acompanhar o hands-on em aula, os seguintes itens devem estar instalados:

- **Java JDK 21** – kit de desenvolvimento compatível com a versão do projeto.
- **Docker** – para disponibilizar os serviços de infraestrutura (bancos de dados, Kafka, EventStoreDB).
- **Postman** – para executar e testar as requisições feitas durante a aula.
- **IntelliJ IDEA** (ou outra IDE de preferência) – para trabalhar com o código-fonte.

## 🧰 Tecnologias Utilizadas

### Stack do projeto:

- **Java 21**
- **Spring Boot**
- **PostgreSQL** (persistência para o lado de comando)
- **MongoDB** (persistência para o lado de leitura)
- **Apache Kafka** (mensageria entre serviços)
- **EventStoreDB** (utilizado apenas na etapa de Event Sourcing)

## 🗂 Estrutura do Projeto

O projeto contém uma estrutura inicial com aplicações e módulos pré-configurados para:

- Comunicação com os bancos de dados (PostgreSQL e MongoDB)
- Integração com o Kafka para propagação de eventos
- Suporte à evolução do projeto para Event Sourcing com o uso do EventStoreDB

## 🚧 Etapas de Implementação

1. **Fase 1 – CQRS (sem Event Sourcing)**  
   Implementação dos microservices com separação entre Command e Query utilizando bancos relacionais e NoSQL.

2. **Fase 2 – Evolução para Event Sourcing**  
   Alteração do modelo de persistência para uso do EventStoreDB como fonte da verdade para eventos.

## 🌿 Branches de Referência

- `cqrs`: implementação completa utilizando CQRS sem Event Sourcing.
- `event-sourcing`: versão do projeto evoluída com Event Sourcing habilitado.

---

Este repositório é um espaço de aprendizado prático. Alunos e alunas são encorajados(as) a explorar, testar e adaptar o projeto conforme os conceitos vistos em aula.

Se tiver dúvidas, traga para a próxima aula ou compartilhe com colegas!
