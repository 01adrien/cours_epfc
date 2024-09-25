<pre>
<?php

function check_and_set($array, $key, $var): void
{
    if (isset($array[$key])) {
        $param = $array[$key];
        if (is_numeric($param)) {
            $var = $param;
        } else {
            echo "<p>min and max must be numeric value</p>";
            exit(1);
        }
    }
}

function main(): void
{

    $min = false;
    $max = false;

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        check_and_set($_POST, "min", $min);
        check_and_set($_POST, "max", $max);
    } else {
        check_and_set($_GET, "max", $max);
        check_and_set($_GET, "min", $min);
    }
    if ($min === false || $max === false) {
        echo "<p>min or max are not set</p>";
        exit(1);
    }

    $max_per_pages = 20;
    $page_number = ($max - $min) / $max_per_pages;
    $need_pagination = $page_number > 1;
    $current_page = isset($_GET["page"]) ? intval($_GET["page"]) : 1;
    $first_item = $min;
    $last_item = $max;

    if ($need_pagination) {

    }

    echo "<ul>";
    if ($max > 0) {
        for ($i = $first_item; $i <= $last_item; $i++) {
            echo "<li>$i</li>";
        }
    }

    if ($need_pagination) {
        if ($current_page > 1) {
            $prev_page = $current_page + 1;
            echo "<a href='./ex6.php?min=$min&max=$max&page=$prev_page'>prev</a>";
        }
        for ($j = 1; $j <= $page_number; $j++) {
            echo " $j ";
        }
        $next_page = $current_page + 1;
        echo "<a href='./ex6.php?min=$min&max=$max&page=$next_page'>next</a>";
    }

    echo "</ul>";

}

main();


?>
</pre>