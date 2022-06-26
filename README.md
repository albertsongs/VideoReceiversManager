# Video receivers manager
API for [remote control](https://albertsongs.github.io/rc) of [the video player](https://albertsongs.github.io/tv) on the [albertsongs.github.io](https://albertsongs.github.io)

# Getting Started

### Steps

To deploy the project in docker container, follow these steps:

1. Copy file certificate.p12 to project folder or create it from .pem files:
   `openssl pkcs12 -inkey key.pem -in certificate.pem -export -out certificate.p12 -CAfile caChain.pem -chain`
2. Fill field SERVER_SSL_KEY_STORE_PASSWORD in docker-compose.yml
3. Run init.sh

## API Requests
### Get video info list
    GET: /api/v1/videos

    Response: 
    200 OK
    {
        "list": [
            {
                "id": 0,
                "title": "Одноклассники мои",
                "youtubeId": "i1HyV58O_DU",
                "youtubePlaylistId": "PLjv4Gt_enoJvL5QUt1H3bv9F3Omuj9n3g"
            },
            {
                "id": 1,
                "title": "Золотая моя рыбка",
                "youtubeId": "DuQ2g-OvYTs",
                "youtubePlaylistId": "PLjv4Gt_enoJvL5QUt1H3bv9F3Omuj9n3g"
            }
        ]
    }
### Get video playlists
    GET: /api/v1/playlists
    
    Response: 
    200 OK
    {
        "list": [
            {
                "id": 0,
                "name": "Нити времени",
                "youtubeId": "PLjv4Gt_enoJvL5QUt1H3bv9F3Omuj9n3g"
            },
            {
                "id": 1,
                "name": "Старые каверы",
                "youtubeId": "PLjv4Gt_enoJvHxigfI9ETQJn6pbzYEX2Q"
            }
        ]
    }
### Get receiver list
    GET: /api/v1/receivers
    
    Response: 
    200 OK
    {
        "list": [
            {
                "id": "1052ccc0-b115-422f-a878-95ac2020cf85",
                "name": "TV",
                "updatedAt": "2022-06-26T08:38:34.609+00:00"
            },
            {
                "id": "8d7404f8-4bd7-47f7-a281-4e3f6e36bcc2",
                "name": "PC",
                "updatedAt": "2022-06-26T08:38:34.604+00:00"
            }
        ]
    }
### Create receivers
    POST: /api/v1/receivers

    Body:
    {
        "name": "Laptop"
    }

    Response: 
    200 OK
    {
        "id": "0080022c-2c86-48f5-b28e-bcf934f2d6ef",
        "name": "Laptop",
        "updatedAt": "2022-06-26T08:48:32.893+00:00"
    }

    Errors:
    400 Bad request
    404 Not found
### Get receiver by ID
    GET: /api/v1/receivers/<receiver_id>

    Response:
    200 OK
    {
       "id": <receiver_id>,
       "name": "Laptop",
       "updatedAt": "2022-06-26T08:48:32.893+00:00"
    }

    Errors:
    404 Not found
### Edit receivers by ID
    PATCH: /api/v1/receivers/<receiver_id>

    Body:
    {
        "name": "New laptop"
    }

    Response:
    200 OK
    {
       "id": <receiver_id>,
       "name": "New laptop",
       "updatedAt": "2022-06-26T08:52:21.922+00:00"
    }

    Errors:
    400 Bad request
    404 Not found
### Remove receiver by ID
    DELETE: /api/v1/receivers/<receiver_id>

    Response:
    200 OK

    Errors:
    404 Not found