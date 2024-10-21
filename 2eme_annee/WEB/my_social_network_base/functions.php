<?php


session_start();

function connect(): PDO
{
    $dbhost = "localhost";
    $dbname = "my_social_network_base";
    $dbuser = "root";
    $dbpassword = "root";
    try {
        $pdo = new PDO("mysql:host=$dbhost;dbname=$dbname;charset=utf8", "$dbuser", "$dbpassword");
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    } catch (Exception $exc) {
        abort("Erreur lors de l'accès à la base de données.");
    }
    return $pdo;
}



function sanitize(string $str): string
{
    return trim(htmlspecialchars($str, ENT_QUOTES, "UTF-8"));
}


function redirect($url, $statusCode = 303)
{
    header('Location: ' . $url, true, $statusCode);
    abort("error redirect");
}

function check_login()
{
    global $user;
    if (!isset($_SESSION['user']))
        redirect('index.php');
    else
        $user = $_SESSION['user'];
}

function abort($err)
{
    global $error;
    $error = $err;
    include 'error.php';
    die;
}