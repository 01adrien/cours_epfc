<pre>
<?php

$min = false;
$max = false;
$max_num;
include './check_values.php';

if(isset($_GET["max_num"])) {
    $max_num = $_GET["max_num"];
} else {
    $max_num = $max - $min;
}


if (isset($_GET["min"])) {
    $min = (int)$_GET["min"];
    $max = (int)$_GET["max"];
} elseif (isset($_POST["min"])) {
    $min = (int)$_POST["min"];
    $max = (int)$_POST["max"];
}


if ($min > $max || $min < 0 || $max < 0) {
    echo "<p>invalid integer value for min or max</p>";
    exit(3);
}


// PAGINATION
$max_per_pages = 20;
$page_number = $max_per_pages / $max_per_pages;
$need_pagination = $page_number > 1;
$max_page = $max_num / $max_per_pages;



if ($need_pagination) {
    $nmin *= $page_number;
    $nmax = $min + $max_per_pages;
    if ($page_number == $max_page) {
        $nmax = $max_num - $nmin;
    }
} else {
    $nmax = $max;
    $nmin = $min;
}

?>
<ul>
<?php
if ($max > 0) {   
    for ($i=$nmin; $i <= $nmax ; $i++) { 
        echo "<li>$i</li>";
    }
}

//if ($need_pagination) {
    include_once 'pagination.php';
//}

?>
</ul>
<pre>