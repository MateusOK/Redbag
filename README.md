# RedBag API

## Objetivo do projeto

O RedBag é um projeto criado na Fatec Registro com o objetivo de proporcionar um diagnóstico rápido e eficiente para cães com catarata. Utilizando redes neurais convolucionais desenvolvemos um modelo preditivo
pra classidicar aniamais com ou sem a condição. Esta API trabalha em conjunto com a API criada em Python para o processamento e envio dos resultados da previsão. RedBag-Python-API pode ser encontrada <a href="https://github.com/MateusOK/RedBag-Python-API">aqui</a>.

## Como rodar o projeto

Para rodar o porjeto RedBag você precisará de uma IDE para rodar a API em SpringBoot, recomendamos o IntelliJ IDEA que pode ser encontrado <a href="https://www.jetbrains.com/idea/download/?section=windows">aqui</a>.

Após abrir o projeto na IDE precisaremos alterar o arquivo **application.yml** para adicionarmos as variáveis de ambiente. Ficará parecido com isso: 

![image](https://github.com/user-attachments/assets/b5db5521-9c1d-4e6c-a60d-9acb1be715c7)


Utilizamos o Cloudinary como nosso banco de imagens, para conseguir as suas chaves bastas acessar https://cloudinary.com/ e criar a sua conta, não disponilizaremos as nossas por questões de segurança.

Você precisará fazer a mesma coisa para o Python: 

![image](https://github.com/user-attachments/assets/65074cfd-129a-4b74-847a-0013b7c8b3e0)

<br></br>
Após isso basta rodar os dois e pronto! Caso queira, você poderá testar as rotas da API através do Swagger acessando: http://localhost:8080/swagger-ui/index.html#/
