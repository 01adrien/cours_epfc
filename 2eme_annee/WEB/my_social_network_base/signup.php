<?php
require_once "functions.php";


$errors = [];
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST["pseudo"]) && strlen($_POST["pseudo"]) > 2) {
        $pseudo = sanitize($_POST["pseudo"]);
        $query = $pdo->prepare("SELECT * FROM Members where pseudo =:pseudo");
        $query->execute(["pseudo" => $pseudo]);
        $user = $query->fetch();
        if ($query->rowCount() == 1) {
            $errors[] = "utilisateur deja existant";
        } else {
            $password = sanitize($_POST["password"]);
            $password_confirm = sanitize($_POST["password_confirm"]);
            if (strcmp($password, $password_confirm) !== 0) {
                $errors[] = "passwords ne correspondent pas";
            } else {
                $_SESSION["user"] = $pseudo;
                global $user;
                $user = $pseudo;
                $sql = "INSERT INTO Members (pseudo, password) VALUES (?,?)";
                $stmt = $pdo->prepare($sql);
                $stmt->execute([$pseudo, $password]);
                redirect("profile.php", 303);
            }
        }
    } else {
        $errors[] = "pseudo obligatoire et de longeur 3 minimum";
    }

}

function get($key, $array)
{
    return isset($array[$key]) ? $array[$key] : "";
}

?>

<!DOCTYPE html>
<html>

<head>
    <title>Sign Up</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="title">Sign Up</div>
    <div class="menu">
        <a href="index.php">Home</a>
    </div>
    <div class="main">
        Please enter your details to sign up :
        <br><br>
        <form action="signup.php" method="post">
            <table>
                <tr>
                    <td>Pseudo:</td>
                    <td><input id="pseudo" name="pseudo" type="text" value=<?php echo $_POST["pseudo"] ?? "" ?>></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input id="password" name="password" type="password" value=<?php echo $_POST["password"] ?? "" ?>>
                    </td>
                </tr>
                <tr>
                    <td>Confirm Password:</td>
                    <td><input id="password_confirm" name="password_confirm" type="password" value=<?php echo $_POST["password_confirm"] ?? "" ?>></td>
                </tr>
            </table>
            <input type="submit" value="Sign Up">
        </form>
        <?php
        if (count($errors) > 0) {
            echo "<div class='errors'><br><br><ul>";
            foreach ($errors as $e) {
                echo "<li>$e</li>";
            }
            echo "</ul></div>";
        }
        ?>
    </div>
</body>

</html>