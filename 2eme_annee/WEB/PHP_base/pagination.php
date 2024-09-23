<?php
    if ($page_number > 1) {
        echo "<a href='./ex6.php?min=1&max=5&max_num=$max_num'>prev</a>";
    }
    echo "<a href='./ex6.php?min=10&max=20&max_num=$max_num'>next</a>";
?>

