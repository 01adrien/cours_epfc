<?php
require_once "functions.php";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST["pseudo"]) && isset($_POST["password"])) {
        $password = sanitize($_POST["password"]);
        $pseudo = sanitize($_POST["pseudo"]);
        $error = null;
        $query = $pdo->prepare("SELECT * FROM Members where pseudo = :pseudo");
        $query->execute(["pseudo" => $pseudo]);
        $user = $query->fetch();
        if ($query->rowCount() == 0) {
            $error = "unknow user..";
        } else {
            if ($password !== $user["password"]) {
                $error = "password dont match..";
            } else {
                echo "OK</br>";
                redirect("http://localhost/my_social_network_base/profile.php?pseudo=$pseudo", 303);
            }
        }
    } else {
        $error = "password or pseudo not provided..";
    }
}
?>


<!DOCTYPE html>
<html>

<head>
    <title>Log In</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="title">Log In</div>
    <div class="menu">
        <a href="index.php">Home</a>
        <a href="signup.php">Sign Up</a>
    </div>
    <div class="main">
        <form action="login.php" method="post">
            <table>
                <tr>
                    <td>Pseudo:</td>
                    <td><input id="pseudo" name="pseudo" type="text" value=""></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input id="password" name="password" type="password" value=""></td>
                </tr>
            </table>
            <input type="submit" value="Log In">
        </form>
        <?php
        if (isset($error))
            echo "<div class='errors'><br><br>$error</div>";
        ?>
    </div>
</body>

</html>