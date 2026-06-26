# ProgressoRH - Configuração de Ambiente

## Variáveis de Ambiente (.env)

O projeto utiliza um arquivo `.env` para armazenar configurações sensíveis e específicas do ambiente. Este arquivo **não deve ser versionado** no Git.

## Setup Inicial

### 1. Copiar o arquivo de exemplo
```bash
cp .env.example .env
```

### 2. Configurar as variáveis
Edite o arquivo `.env` e altere os valores sensíveis, especialmente:
- `MYSQL_PASSWORD`
- `MYSQL_ROOT_PASSWORD`
- `SPRING_DATASOURCE_PASSWORD`

### 3. Iniciar a aplicação
```bash
docker compose up -d
```

## Variáveis Disponíveis

### Database (MySQL)
- `MYSQL_DATABASE` - Nome do banco de dados (padrão: rh_sistema)
- `MYSQL_USER` - Usuário não-root (padrão: admin)
- `MYSQL_PASSWORD` - Senha do usuário admin
- `MYSQL_ROOT_PASSWORD` - Senha do root
- `DB_HOST` - Host do banco (padrão: mysql-db)
- `DB_PORT` - Porta do banco (padrão: 3306)

### Spring Boot Application
- `SPRING_DATASOURCE_URL` - URL de conexão do banco de dados
- `SPRING_DATASOURCE_USERNAME` - Usuário de conexão (padrão: root)
- `SPRING_DATASOURCE_PASSWORD` - Senha de conexão
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME` - Driver JDBC (padrão: com.mysql.cj.jdbc.Driver)
- `SPRING_APPLICATION_NAME` - Nome da aplicação (padrão: progressoRH)
- `SPRING_SQL_INIT_MODE` - Modo de inicialização SQL (padrão: always)
- `SPRING_JPA_HIBERNATE_DDL_AUTO` - Modo de criação de tabelas (padrão: update)
- `SPRING_JPA_SHOW_SQL` - Mostrar SQL no console (padrão: true)
- `SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL` - Formatar SQL (padrão: true)

### Server
- `SERVER_PORT` - Porta da aplicação (padrão: 8080)

## Segurança

⚠️ **IMPORTANTE:**
- **Nunca commit .env** - Use `.gitignore` para excluir este arquivo
- **Use .env.example** - Para documentar quais variáveis são necessárias
- **Altere as senhas padrão** - Em ambiente de produção, utilize senhas fortes
- **Use secrets em produção** - Docker Secrets, Vault, ou serviços de gerenciamento de secrets

## Exemplo de .env (Desenvolvimento)

```env
MYSQL_DATABASE=rh_sistema
MYSQL_USER=admin
MYSQL_PASSWORD=sua_senha_forte_aqui
MYSQL_ROOT_PASSWORD=root_senha_forte_aqui
DB_HOST=mysql-db
DB_PORT=3306

SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/rh_sistema
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root_senha_forte_aqui
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

SPRING_APPLICATION_NAME=progressoRH
SPRING_SQL_INIT_MODE=always
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true

SERVER_PORT=8080
```

## Troubleshooting

### Erro: "Unknown database 'rh_sistema'"
- Verifique se `MYSQL_DATABASE` está configurado corretamente
- Certifique-se de que o schema foi inicializado (verifique `sql/sistema-rh_db.sql`)

### Erro: "Access denied for user 'root'"
- Confirme que `SPRING_DATASOURCE_PASSWORD` corresponde a `MYSQL_ROOT_PASSWORD`
- Aguarde o container MySQL ficar healthy antes de iniciar a app

### Erro: "Cannot determine a valid SQL dialect"
- Verifique se `SPRING_DATASOURCE_DRIVER_CLASS_NAME` está correto
- Confirme que `SPRING_DATASOURCE_URL` está válida

## Docker Compose com .env

O Docker Compose automaticamente carrega variáveis do `.env`:

```bash
# Inicia com variáveis do .env
docker compose up -d

# Verifica as variáveis carregadas
docker compose config
```
