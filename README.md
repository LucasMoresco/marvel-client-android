# Android Client for Marvel API

## Requisitos

* Android Studio 3.1.2
* Android 5.0 (API Level 21)

## Dependências

* [Retrofit 2.4.0](https://github.com/square/retrofit)
* [Okhttp 3.10.0](https://github.com/square/okhttp)
* [Picasso 2.71828](https://github.com/square/picasso)

## Instalação e Configurações

Siga as instruções para rodar o projeto.

1. Clone o repositório.
2. [Instale o Android Studio](https://developer.android.com/sdk/index.html).
3. Importe o projeto. Abra o Android Studio, click em 'Open... e selecione o projeto. Gradle irá construir o projeto.
4. Adicione as chaves de API da Marvel no seu arquivo **local.properties** (conforme o exemplo abaixo).
5. Rode o app. Click 'Run > Run 'app''.



### Adição das chaves de API da Marvel
  Para não expor as minhas chaves de acesso a API eu as removi do repositório, desta forma, você terá que adicionar as suas próprias chaves.
  Você pode encontrá-las [aqui](https://developer.marvel.com).

* Adicione as seguintes linhas no seu arquivo **local.properties** e build o projeto.
 
MARVEL_PUBLIC_KEY="**SUA_MARVEL_PUBLIC_KEY**"   
MARVEL_PRIVATE_KEY="**SUA_MARVEL_PRIVATE_KEY**"
