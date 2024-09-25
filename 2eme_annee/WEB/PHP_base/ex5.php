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
    $page_number = $max_per_pages / $max_per_pages;
    $need_pagination = $page_number > 1;
    $max_page = $max / $max_per_pages;
    $current_page = isset($_GET["page"]) ? intval($_GET["page"]) : 1;

    if ($need_pagination) {

    }

    echo "<ul>";
    if ($max > 0) {
        for ($i = $min; $i <= $max; $i++) {
            echo "<li>$i</li>";
        }
    }

    if ($need_pagination) {
        if ($current_page > 1) {
            echo "<a href='./ex6.php?min=$min&max=$max&page=$current_page'>prev</a>";
        }
        echo "<a href='./ex6.php?min=$min&max=$max&page=$current_page'>next</a>";
    }

    echo "</ul>";

}

main();


?>
</pre>