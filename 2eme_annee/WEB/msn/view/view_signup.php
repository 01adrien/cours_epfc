<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sign Up</title>
        <base href="<?= $web_root ?>"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <script src="<?= $web_root ?>js/view_signup.js"></script>
    </head>

    <style>
        .error {
            font-size: small;
        }
    </style>

    <body>
        <div class="title">Sign Up</div>
        <div class="menu">
            <a href="index.php">Home</a>
        </div>
        <div class="main">
            Please enter your details to sign up :
            <br><br>
            <form id="signupForm" action="main/signup" method="post">
                <table>
                    <tr id="row1">
                        <td>Pseudo:</td>
                        <td><input id="pseudo" name="pseudo" type="text" size="16" value="<?= $pseudo ?>" oninput="checkPseudo(this)">
                        <span class="error" style="color: red;"></span>
                    </td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input id="password" name="password" type="password" size="16" value="<?= $password ?>" oninput="checkPassword(this)">
                        <span class="error" style="color: red;"></span>
                    </td>
                    </tr>
                    <tr>
                        <td>Confirm Password:</td>
                        <td><input id="password_confirm" name="password_confirm" type="password" size="16" value="<?= $password_confirm ?>" oninput="checkConfirmPassword(this)">
                        <span class="error" style="color: red;"></span>
                    </td>
                    </tr>
                </table>
                <input type="submit" value="Sign Up">
            </form>
            <?php if(count($errors)!=0): ?>
                <div class='errors'>
                    <br><br><p>Please correct the following error(s) :</p>
                    <ul>
                    <?php foreach($errors as $error): ?>
                        <li><?= $error ?></li>
                    <?php endforeach; ?>
                    </ul>
                </div>
            <?php endif; ?>
        </div>
    </body>
</html>