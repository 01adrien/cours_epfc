<!DOCTYPE html>
<html>

<head>
    <title>Members</title>
    <meta charset="UTF-8">
    <base href="<?= $web_root ?>" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="title">Members</div>
    <?php include 'view/menu.html'; ?>
    <div class="main">
        <ul>
            <?php for ($i = 0; $i < count($members); $i++): ?>
                <li><a href='member/profile/<?= $members[$i]->pseudo ?>'><?= $members[$i]->pseudo ?></a>
                    <?php if ($relations[$i]['follower'] == 1 && $relations[$i]['followee'] == 1): ?>
                        <div>
                            <p><-> is a mutual friend</p>
                        </div>
                    <?php elseif ($relations[$i]['follower'] == 1): ?>
                        <div>
                            <p><- you are following</p>
                        </div>
                    <?php elseif ($relations[$i]['followee'] == 1): ?>
                        <div>
                            <p>-> is following you</p>
                        </div>
                    <?php else: ?>
                        <p>follow</p>
                    <?php endif ?>

                <?php endfor; ?>
        </ul>
    </div>
</body>

</html>