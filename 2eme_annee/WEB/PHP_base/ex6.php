<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="style.css">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <div class="main">
        <?php
        if (!isset($_GET["min"]) || !isset($_GET["max"])) {
            include_once './form.php';
        } else {
            include_once './ex5.php';
        }
        ?>
    </div>
</body>

</html>