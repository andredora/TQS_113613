# Etapa 1: Construção da aplicação
FROM node:20-alpine AS build

WORKDIR /app

# Copiar o package.json e o package-lock.json
COPY package.json package-lock.json ./

# Instalar dependências
RUN npm install

# Copiar o código-fonte e gerar o build
COPY . .
RUN npm run build

# Etapa 2: Servir a aplicação com http-server
FROM node:20-alpine

WORKDIR /app

# Instalar o http-server para servir os arquivos de build
RUN npm install -g http-server

# Copiar os arquivos gerados do build
COPY --from=build /app/build /app/build

# Expor a porta que o servidor vai usar
EXPOSE 80

# Comando para rodar o servidor com a opção -s para SPA
CMD ["http-server", "build", "-p", "80", "-s"]
