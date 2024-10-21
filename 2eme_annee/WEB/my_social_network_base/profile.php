<?php
require_once "functions.php";

check_login();

if (isset($_GET["pseudo"])) {
    $pseudo = sanitize($_GET["pseudo"]);

} else {
    $pseudo = $user;
}

$pdo = connect();
$query = $pdo->prepare("SELECT * FROM Members where pseudo = :pseudo");
$query->execute(array("pseudo" => $pseudo));
$profile = $query->fetch();
if ($query->rowCount() == 0) {
    abort("L'utilisateur n'existe pas");
} else {
    $description = $profile["profile"] ?? "";
    $picture_path = $profile["picture_path"] ?? "";
}
?>

<!DOCTYPE html>
<html>

<head>
    <title>TODO's Profile!</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="title"><?php echo $pseudo; ?>`s Profile!'s Profile!</div>
    <?php include "menu.php" ?>
    <div class="main">
        <?php
        if (strlen($description) == 0) {
            echo 'No profile string entered yet!';
        } else {
            echo $description;
        }
        ?>
        <br><br>
        <?php
        if (strlen($picture_path) == 0) {
            echo 'No picture loaded yet!';
        } else {
            echo "<image src='images/$picture_path' width='100' alt='$pseudo&apos;s photo!'>";
        }

        ?>
    </div>
</body>

</html>