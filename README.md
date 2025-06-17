# 🍽️ JotaveFood - Sistema de Pedidos e Pagamentos com Microsserviços

Bem-vindo ao **JotaveFood**, um sistema desenvolvido com **arquitetura de microsserviços**, idealizado para simular o fluxo completo de **pedidos e pagamentos** em um ambiente de produção moderno. Esse projeto tem como foco a escalabilidade, a resiliência e a experiência prática com o ecossistema Spring Cloud.

> 💡 Este projeto foi construído como parte do meu portfólio para demonstrar domínio de **Java com Spring Boot**, princípios de **microsserviços**, **comunicação entre serviços**, **descoberta de serviços**, e **tolerância a falhas**.

---

## 🧱 Estrutura do Projeto

```
jotavefood/
├── pagamentos/     # Microsserviço para gerenciamento de pagamentos
├── pedidos/        # Microsserviço para controle de pedidos
├── gateway/        # API Gateway para roteamento de requisições
├── server/         # Eureka Server para descoberta de serviços
```

Cada serviço possui sua própria base de código, base de dados e ciclo de vida independente.

---

## 🌟 Funcionalidades Implementadas

✅ Descoberta de serviços com **Eureka Server**  
✅ Comunicação entre serviços com **OpenFeign**  
✅ Resiliência com **Resilience4j** (fallback em falhas)  
✅ Roteamento com **Spring Cloud Gateway**  
✅ Migrations com **Flyway**  
✅ Integração com **MySQL** usando **Spring Data JPA**  
✅ Validações com **Bean Validation**  
✅ Monitoramento visual de serviços via Eureka Dashboard  

---

## 🚀 Tecnologias e Ferramentas

| Categoria              | Tecnologias Utilizadas                                  |
|------------------------|----------------------------------------------------------|
| **Linguagem**          | Java 17                                                 |
| **Frameworks**         | Spring Boot 3.3, Spring Data JPA, Spring Cloud          |
| **Service Discovery**  | Eureka Server                                           |
| **Comunicação**        | OpenFeign                                               |
| **Tolerância a falhas**| Resilience4j                                            |
| **Persistência**       | MySQL + Flyway                                          |
| **APIs REST**          | Spring Web + Bean Validation                            |
| **Ferramentas Dev**    | Lombok, DevTools, Maven                                 |
| **Testes**             | Spring Boot Test                                        |

---

## 🔁 Comunicação entre Microsserviços

### 🧾 `pagamentos` → `pedidos`
- Usa **OpenFeign** para consultar pedidos relacionados
- Configura fallback com **Resilience4j** para evitar falhas em cascata

### 🔎 Descoberta com Eureka
- Todos os serviços se registram automaticamente no **Eureka Server**
- O `gateway` e os serviços usam **load balancing** (`lb://`) baseado nos nomes dos serviços

---

## 🗄️ Banco de Dados

- Cada serviço tem sua **própria base de dados** (isolamento de dados)
- **Flyway** garante controle de versões das tabelas
- Integração com **MySQL** via **Spring Data JPA**

---

## 📋 Como Executar Localmente

### Pré-requisitos
- Java 17
- MySQL 8+
- Maven

### Passos

1. Clone o projeto:
   ```bash
   git clone https://github.com/seu-usuario/jotavefood.git
   ```

2. Crie os bancos de dados:
   - `jotavefood-pagamento`
   - `jotavefood-pedido com a tabela item_do_pedido (armazena os detalhes de cada pedido)`

3. Configure os arquivos `application.properties` de cada serviço com suas credenciais MySQL (usuário, senha, url, etc).

4. Execute os serviços na seguinte ordem:

   ```
   1. server      (porta fixa: 8081)
   2. gateway     (porta fixa: 8082)
   3. pedidos     (porta dinâmica: atribuída automaticamente)
   4. pagamentos  (porta dinâmica: atribuída automaticamente)
   ```

   > ⚠️ Importante: Os serviços pedidos e pagamentos registram-se automaticamente no Eureka com uma porta aleatória. Certifique-se de que o servidor Eureka (server) esteja ativo antes de iniciar esses serviços, para que possam se registrar corretamente.

   ---  

5. Acesse o Eureka Dashboard: http://localhost:8081

---

## 🧠 Aprendizados e Motivação

Este projeto foi criado com o objetivo de:

- Consolidar os conceitos de **microsserviços**
- Praticar **comunicação entre serviços** com fallback
- Explorar ferramentas modernas como **Eureka, Feign, Resilience4j e Flyway**
- Criar uma estrutura escalável e robusta
- Demonstrar na prática o uso de **Spring Cloud** em um ambiente distribuído

---

## 📈 Possíveis Evoluções

- ✅ Autenticação com Spring Security e JWT  
- ⏳ Integração com mensageria (Kafka ou RabbitMQ)  
- ⏳ Observabilidade (Prometheus + Grafana)  
- ⏳ Testes de contrato (Spring Cloud Contract)  
- ⏳ Docker + Docker Compose para orquestração  
- ⏳ Deploy em ambiente cloud (Heroku, AWS, etc.)

---

## 👨‍💻 Sobre o Autor

Jean Victor é apaixonado por tecnologia, inovação e projetos que impactam a vida real. Com experiência prática no ecossistema Spring, busca constantemente novas formas de aprender, compartilhar e evoluir.

📧 **Contato**: [jvrferreira96@gmail.com](mailto:jvrferreira96@gmail.com)  
🔗 **LinkedIn**: [linkedin.com/in/jotave-erref](https://www.linkedin.com/in/jotave-erref/)  
💼 **Portfólio Completo**: [https://github.com/seu-usuario](https://github.com/seu-usuario)

---

Feito com 💛, café e muitas classes Java ☕
