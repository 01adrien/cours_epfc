<?php
require_once "functions.php";
check_login();
$success = false;

$query = $pdo->prepare("SELECT * FROM Members where pseudo = :pseudo");
$query->execute(["pseudo" => $user]);
$user = $query->fetch();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $description = sanitize($_POST["description"] ?? "");
    $picture_path = sanitize($_POST["picture_path"] ?? "");
    $query = $pdo->prepare(
        "Update Members SET profile =:profile, picture_path =:picture_path WHERE pseudo =:pseudo"
    );
    $query->execute(["pseudo" => $user, "picture_path" => $picture_path, "description" => $description]);
}

?>


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
    <div class="menu">
        <a href="profile.php">Home</a>
        <a href="members.php">Members</a>
        <a href="friends.php">Friends</a>
        <a href="messages.php">Messages</a>
        <a href="edit_profile.php">Edit Profile</a>
        <a href="logout.php">Log Out</a>
    </div>
    <div class="main">
        <form method='post' action='edit_profile.php' enctype='multipart/form-data'>
            <p>Enter or edit your details and/or upload an image.</p>
            <textarea name='profile' cols='50' rows='3'>
                <?php echo $user["profile"] ?? "" ?>
            </textarea><br><br>

            Image: <input type='file' name='image' accept="image/x-png, image/gif, image/jpeg"><br><br>
            <image src=<?php echo $user["picture_path"] ?? "" ?>><br><br>

                <input type='submit' value='Save Profile'>
        </form>
        <?php
        if ($success) {
            echo '<span class="succes">OK</span>';
        }
        ?>
    </div>
</body>

</html>