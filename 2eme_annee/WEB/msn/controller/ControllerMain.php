<?php

require_once 'model/Member.php';
require_once 'framework/View.php';
require_once 'framework/Controller.php';

class ControllerMain extends Controller
{

    //si l'utilisateur est connecté, redirige vers son profil.
    //sinon, produit la vue d'accueil.
    public function index(): void
    {
        if ($this->user_logged()) {
            $this->redirect("member", "profile");
        } else {
            (new View("index"))->show();
        }
    }

    //gestion de la connexion d'un utilisateur
    public function login(): void
    {
        $pseudo = '';
        $password = '';
        $errors = [];
        if (isset($_POST['pseudo']) && isset($_POST['password'])) { //note : pourraient contenir des chaînes vides
            $pseudo = $_POST['pseudo'];
            $password = $_POST['password'];

            $errors = Member::validate_login($pseudo, $password);
            if (empty($errors)) {
                $this->log_user(Member::get_member_by_pseudo($pseudo));
            }
        }
        (new View("login"))->show(["pseudo" => $pseudo, "password" => $password, "errors" => $errors]);
    }

    public function signup(): void
    {
        $pseudo = '';
        $password = '';
        $password_confirm = '';
        $errors = [];
        if (isset($_POST['pseudo']) && isset($_POST['password']) && isset($_POST['password_confirm'])) {
            $pseudo = trim($_POST['pseudo']);
            $password = $_POST['password'];
            $password_confirm = $_POST['password_confirm'];

            $member = new Member($pseudo, password_hash($password, PASSWORD_BCRYPT));
            $errors = Member::validate_unicity($pseudo);
            $errors = array_merge($errors, $member->validate());
            $errors = array_merge($errors, Member::validate_passwords($password, $password_confirm));

            if (count($errors) == 0) {
                $member->persist(); //sauve l'utilisateur
                $this->log_user($member);
            }
        }
        (new View("signup"))->show(["pseudo" => $pseudo, "password" => $password, "password_confirm" => $password_confirm, "errors" => $errors]);

    }
}
