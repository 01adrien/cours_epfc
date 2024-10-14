<?php
require_once("functions.php");
$_SESSION = [];    //on écrase le tableau
session_destroy();      //on détruit la session
redirect('index.php');  //le visiteur est redirigé à l'index
