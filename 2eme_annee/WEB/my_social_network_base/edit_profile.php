<pre>

    <?php
    require_once "functions.php";
    check_login();
    $success = false;

    $pdo = connect();
    $query = $pdo->prepare("SELECT * FROM Members where pseudo = :pseudo");
    $query->execute(["pseudo" => $user]);
    $current_user = $query->fetch();
    $picture_path = "";
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $succes = "";
        $description = sanitize($_POST["profile"] ?? "");
        $picture_path = $current_user["picture_path"];
        $query = $pdo->prepare(
            "Update Members SET profile =:profile, picture_path =:picture_path WHERE pseudo =:pseudo"
        );
        if (isset($_FILES['image']['name']) && $_FILES['image']['name'] != '') {
            if ($_FILES['image']['error'] == 0) {

                $typeOK = TRUE;
                if ($_FILES['image']['type'] == "image/gif")
                    $saveTo = $user . ".gif";
                else if ($_FILES['image']['type'] == "image/jpeg")
                    $saveTo = $user . ".jpg";
                else if ($_FILES['image']['type'] == "image/png")
                    $saveTo = $user . ".png";
                else {
                    $typeOK = FALSE;
                    $error = "Mauvais type d'image : gif, jpeg ou png !";
                }
                if ($typeOK) {
                    if ($picture_path)
                        unlink("images/" . $picture_path);
                    move_uploaded_file($_FILES['image']['tmp_name'], to: "images/" . $saveTo);
                    $success = "Votre profil a été correctement mis à jour !";
                    $picture_path = $saveTo;
                }
            } else {
                $error = "Problème lors du chargement de l'image";
            }
        }
        $query->execute(["pseudo" => $user, "picture_path" => $picture_path, "profile" => $description]);

    }

    ?>
</pre>


<!DOCTYPE html>
<html>

<head>
    <title><?php echo $user ?>'s Profile</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="title">Update Your Profile</div>
    <?php include "menu.php" ?>
    <div class="main">
        <form method='post' action='edit_profile.php' enctype='multipart/form-data'>
            <p>Enter or edit your details and/or upload an image.</p>
            <textarea name='profile' cols='50' rows='3'>
                <?php echo $current_user["profile"] ?? "" ?>
            </textarea><br><br>

            Image: <input type='file' name='image' accept="image/x-png, image/gif, image/jpeg"><br><br>
            <image src=<?php echo "images/" . $picture_path ?? "" ?>><br><br>

                <input type='submit' value='Save Profile'>
        </form>
        <?php
        if ($success) {
            echo "<span class='succes'>$succes</span>";
        }
        ?>
    </div>
</body>

</html>