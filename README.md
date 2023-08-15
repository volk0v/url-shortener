# url-shortener

## Description

HTTP service for shortening URLs like Bitly etc. Available saving URLs and getting short name for redirecting. Also, you
can give your own shortened name for saving URL.

Shortened name is random-generated string of letters and digits with [specified length](#shortened-names-length).

Application uses [MariaDB](#mariadb-config) for saving data.

## Using

Application can be used in three ways. In two ways except of already completed Docker Compose config, you must set up
[MariaDB](#mariadb-config). For using application, you should read about [endpoints](#endpoints).

### Fast use. Docker Compose

Application can be used via Docker Compose. If you want to use already completed
[Docker Compose config](docker-compose.yml) you should do this:

1. Clone the repository and go to the repository's directory:

``` shell
git clone https://github.com/volk0v/url-shortener.git && cd url-shortener
```

2. Run the services (url-shortener and mariadb) by using Docker Compose config:

```shell
docker-compose up -d
```

Done, now you can connect it via your own _IP address_ or _localhost_.

### Building Docker image

You can build a Docker image and use it by your way, for it, you should do this:

1. Clone the repository and go to the repository's directory:

``` shell
git clone https://github.com/volk0v/url-shortener.git && cd url-shortener
```

2. Build an image:

```shell
docker build -t url-shortener:latest
```

3. Create container and start application:

```shell
docker run -p 80:8080 -d url-shortener:latest
```

Done, now you can connect it via your own _IP address_ or _localhost_.

### Building jar file

You can build a jar file, for it, you should do this:

1. Clone the repository and go to the repository's directory:

``` shell
git clone https://github.com/volk0v/url-shortener.git && cd url-shortener
```

2. Run Maven for build jar file:

```shell
./mvnw clean package
```

3. Now you built jar file which is in _target_ directory. Run it:

```shell
java -jar target/url-shortener*.jar
```

Done, now you can connect it via your own _IP address_ or _localhost_ and **you should use port 8080!**

## Endpoints

### POST `/registration`

Use it for creating shortened name for your URL.

Body for creating _**random-generated** shortened name_:

```json
{
  "referenceUrl": "https://example.net"
}
```

Body for creating _**your own** shortened name_:

```json
{
  "referenceUrl": "https://example.net",
  "shortenedName": "example"
}
```

Response is JSON with reference URL and shortened name (random-generated or your own):

```json
{
  "referenceUrl": "https://example.net",
  "shortenedName": "example"
}
```

### GET `/created-shortened-name`

Use it for get redirect to your reference URL.

## Environment variables

### Shortened name's length

You can change the length of random-generated shortened name by setting up the environment
**url-shortener.random-url-length**. By standard, it set up for seven characters. Example:

``` properties
url-shortener.random-url-length=10
```

### MariaDB config

You **_must_** set up database configuration by setting environment variables: **DB_URL**, **DB_USERNAME**,
**DB_PASSWORD**. Example:

```properties
DB_URL=jdbc:mariadb://localhost/app_database
DB_USERNAME=app_user
DB_PASSWORD=P@ssw0rd
```




