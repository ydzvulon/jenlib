# Decks or docker compositions

## Availble Decks

### Ngnix Deck

> `from_url`: https://stackoverflow.com/questions/57621945/docker-configure-docker-compose-and-nginx-to-have-jenkins-behind-nginx

```yaml
nginx:
    image: nginx:1.17-alpine
    container_name: nginx-docker
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./data/nginx:/etc/nginx/conf.d
      - ./data/html:/etc/nginx/html
  jenkins:
    image: "jenkins/jenkins:lts"
    container_name: jenkins-docker
    volumes:
      - ./data/jenkins:/var/jenkins_home
    expose:
      - "8080"
```