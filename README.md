# my-yt-dl

a "front-end" for yt-dlp. 
en effet ca permet de telechercher la version que l'ont desire.  
les commande de base de yt-dlp sont complexe et choisir la bonne qualité ne fontionne pas simplement.  
J'ai vraiment fait un "front-end" simplifier a bloc.


# marche a suivre

il faut cree un fichier `conf.conf` 
```json
{
    "port": 9998,
    "ytp_dlpExe": "/home/myhome/yt-dlp/yt-dlp",
    "ffmpegExe": "ffmpeg"
}
```

port : le port du server  
ytp_dlpExe dlpExe: ou se trouve l'éxecutable yt-dlp
ffmpegExe: pour le moment ne sert a rien.

# Utilisation

il n'y a que 2 routes.  
Les 2 sont en POST.
```
api/infos
api/download
```

## infos
```json
{
    "link" : "lien youtube"
    "audioOnly" : true|false
    "chapter": true|false
    "giveChapter" : string
}


```