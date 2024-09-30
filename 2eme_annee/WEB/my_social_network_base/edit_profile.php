<!DOCTYPE html>
<html>
    <head>
        <title>TODO's Profile</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="styles.css" rel="stylesheet" type="text/css"/>
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
                <textarea name='profile' cols='50' rows='3'></textarea><br><br>

                Image: <input type='file' name='image' accept="image/x-png, image/gif, image/jpeg"><br><br>
                <image src=''><br><br>

                <input type='submit' value='Save Profile'>
            </form>
        </div>
    </body>
</html>

