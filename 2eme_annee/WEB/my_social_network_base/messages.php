<!DOCTYPE html>
<html>

<head>
    <title>TODO's Messages</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="title">TODO's Messages</div>
    <div class="menu">
        <?php include "menu.php" ?>
    </div>
    <div class="main">
        <form action="messages.php?member=XXX" method="post">
            Type here to leave a message:<br>
            <textarea id="body" name="body" rows='3'></textarea><br>
            <input id="private" name="private" type="checkbox">Private message<br>
            <input type="submit" value="Post">
        </form>
        <p>These are TODO's messages:</p>
        <table class="message_list">
            <tr>
                <th>Date/Time</th>
                <th>Author</th>
                <th>Message</th>
                <th>Private?</th>
                <th>Action</th>
            </tr>
        </table>
    </div>
</body>

</html>