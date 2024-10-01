<pre>
<?php

function check_and_get($array, $key): int
{
    if (isset($array[$key])) {
        $param = $array[$key];
        if (is_numeric($param)) {
            return (int) $param;
        } else {
            echo "<p>min and max must be numeric value</p>";
            exit(1);
        }
    }
    return -1;
}

function main(): void
{
    $min = -1;
    $max = -1;
    switch ($_SERVER["REQUEST_METHOD"]) {
        case "POST":
            $min = check_and_get($_POST, "min");
            $max = check_and_get($_POST, "max");
            break;
        default:
            $min = check_and_get($_GET, "min");
            $max = check_and_get($_GET, "max");
            break;
    }

    if ($min <= 0 || $max <= 0) {
        echo "<p>min or max are not set</p>";
        exit(1);
    }

    $max_per_pages = isset($_GET["per_page"])
        ? intval($_GET["per_page"])
        : (isset($_POST["per_page"])
            ? intval($_POST["per_page"])
            : 20);

    $page_number = ceil(($max - $min) / $max_per_pages);
    $need_pagination = $page_number > 1;
    $current_page = isset($_GET["page"]) ? intval($_GET["page"]) : 1;
    $first_item = $min;
    $last_item = $max;

    if ($need_pagination) {
        $first_item = $min + $max_per_pages * ($current_page - 1);
        $last_item = ($current_page == $page_number) ? $max : $first_item + ($max_per_pages - 1);
    }
    echo "<a href=./ex6.php>form</a>";
    echo "<ul>";
    if ($max > 0) {
        for ($i = $first_item; $i <= $last_item; $i++) {
            echo "<li>$i</li>";
        }
    }
    echo "</ul>";

    if ($need_pagination) {
        echo "<div>";
        if ($current_page > 1) {
            $prev_page = $current_page - 1;
            echo "<a href='./ex6.php?min=$min&max=$max&page=$prev_page&per_page=$max_per_pages'>prev</a> ";
        }
        for ($j = 1; $j <= $page_number; $j++) {
            $color = ($current_page == $j) ? "red" : "blue";
            echo "<a style='color:$color' href='./ex6.php?min=$min&max=$max&page=$j&per_page=$max_per_pages'>$j</a> ";
        }
        if ($current_page != $page_number) {
            $next_page = $current_page + 1;
            echo "<a href='./ex6.php?min=$min&max=$max&page=$next_page&per_page=$max_per_pages'>next</a>";
        }
        echo "</div>";
    }

}

main();


?>
</pre>