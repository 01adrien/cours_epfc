<?php
    if (!isset($_GET["min"]) || !isset($_GET["max"])) {
        include_once './form.php';
    } else {
        include_once './ex5.php';
    }
?>