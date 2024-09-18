# Types d'adresses IPV4 #

![image](./images/ipv6-adressing.png)

## <u>adresse unicast</u>  ##

1) **loopback** ::1/128 || 127.0.0.0/8
2) **link local**  FE80::/10 || 169.254.0.0/16 (APIPA pas de dhcp, reseau local, pas de redirection des paquets )
3) **global** 2001::/16 || public IP
4) **unspecified** ::/128 || 0.0.0.0 (*)
5) **site-local**  FEC0::/10 || private adresse (replace with unique local address (ULA))
6) **ULA** FC00::/7 || private LAN
7) **IPV4 compatible** 0:0:0:0:0:0::/96 
   
### <u>Link local:</u> ###
&nbsp;
![image](./images/link-local-address.png)

adresse link local generee par le PC au moment ou il entre sur le reseau <br/>
**E**xtended **U**nique **I**dentifier, c'est le systeme qui convertit les 48 bits de la mac adresse en 64 bits. (ajoute FFFE au milieu).   

### <u>global (public):</u> ### 

![image](./images/global-address.png)
&nbsp;

toutes les adresses publiques commencent par 2001 (/16 ouvert par IANA) <br/><br/>
composition des 64 premiers bits<br/>
&nbsp;
<img src="./images/64-first-bit-global-address.png" height="300px">
&nbsp;

les 64 suivants sont generes par EUI 64;
global routing prefix et subnet id viennent de la passerelle

<u>**N**eighbour **D**iscovery **P**rotocol:</u><br/>
Comme ARP en IPV4 sauf que c'est un protocol de couche 3.
utilise IMCPv6