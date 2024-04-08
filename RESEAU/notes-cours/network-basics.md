<link rel="stylesheet" href="./style.css"></link>

# LES RESEAUX

## Materiel: 

HUB:
- Renvoie a tous le monde
- topologie logique bus
- pas inteligent
- collisions (CSMA - CD)

SWITCH:
- renvoie a une seule machine
- mac adresse
- table cam
- pas de colisions

**topologie physique != topologie logique**

token ring => les machines se passent un jeton pour parler 

<br/>
<p class="txt-center" >Structure d'un paquet reseau</p>
<img src="./images/networkpacket.webp" class="img-center" width="500px">
<br/>
<br/>

# OSI et TCP/IP #

<br/>
<img src="./images/osi-vs-tcpip.jpg" >

## Couche transport (TCP)

<img src="./images/tcp_header.jpg" width="500px" class="img-center">
<p class="txt-center">TCP header</p>
<br>
<img src="./images/tcp-connect.png" class="img-center">
<p class="txt-center">TCP connection schema</p>
<br>
<img src="./images/tct-connect-wireshark.png">
<p class="txt-center">TCP connection process (wireshark)</p>


### <u>SEQ , ACK & WIN</u>

Le numero de sequence et le numero d'acquitement suivent les paquets et garantissent que rien ne se perd.<br>
Ils sont incrementes par le nombre de bytes envoyes<br>
Les flags SYN et ACK envoie un byte.

<img src="./images/seq-ack.png" class="img-center" width="500px">  

## Couche reseau

**IGP** : Intern Gateway Protocol<br>
**EGP** : Extern Gateway Protocol

<p class="txt-center">Protocole de routage</p>
<img src="./images/protocole-de-routage.png"/>