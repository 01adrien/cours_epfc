<?php
$str = "string";
echo "length $str = " . strlen($str) . "<br>";
$str = "jim@gmail.com";
echo str_contains($str, "@") . "<br>";
$str = "@jim@gmail.com";
echo str_starts_with($str, "@") . "<br>";
echo substr($str, 1) . "<br>";
$str = "h e l l o";
print_r(explode(" ", $str));
echo "<br>";
echo trim("   hello   ") . "<br>";

?>