<?php

if (isset($_GET["min"])) {
    $min = $_GET["min"];
}

if (isset($_GET["max"])) {
    $max = $_GET["max"];
}

if (isset($_POST["min"])) {
    $min = $_POST["min"];
}

if (isset($_POST["max"])) {
    $max = $_POST["max"];
}

if ($min === false || $max === false) {
    echo "<p>min or max are not set</p>";
    exit(1);
}

if (!(is_numeric($min) && is_numeric($max))) {
    echo "min or max are not integer";
    exit(2);
}
