<?php

require_once 'model/Member.php';
require_once 'framework/View.php';
require_once 'framework/Controller.php';


class ControllerMessage extends Controller {
    public function index(): void {
        var_dump('');
        $user = $this->get_user_or_redirect();
        $recipient = $this->get_recipient($user);
        $messages = $recipient->get_messages();
        $errors = $this->post_message($user, $recipient);
        (new View("messages"))
            ->show(["errors" => $errors, "messages" => $messages, 
                          "recipient" => $recipient, "user" => $user]);
    }

    public function delete(): void {
        $user = $this->get_user_or_redirect();
        $recipient = $this->get_recipient($user);
        if (isset($_POST['param']) && $_POST['param'] != "")
        {
            $post_id = $_POST['param'];
            $message = Message::get_message($post_id);
            if ($message && ($message->author == $user || $message->recipient == $user))
            {
                $user->delete_message($message);
                $member = $message->recipient;
                $this->redirect('message', 'index', $recipient->pseudo);
            }
            else{
                Tools::abort("Wrong/missing param or action no permited");
            }
        }
        else{
            Tools::abort("Wrong/missing param or action no permited");   
        }
    }

    function get_recipient($user){
        if(!isset($_GET["param1"]) || $_GET["param1"]==""){
            return $user;
        } else{
            return Member::get_member_by_pseudo($_GET["param1"]);
        }
    }

    function post_message(Member $user, Member $recipient): array {
        $errors = [];
        if (isset($_POST['body'])) {
            $body = $_POST['body'];
            $private = isset($_POST['private']) ? TRUE : FALSE;
            $message = new Message($user, $recipient, $body, $private);
            $errors = $message->validate();
            if(empty($errors)){
                $user->write_message($message);                
            }
            $this->redirect('message', 'index', $recipient->pseudo);
       }
       return $errors;
    }

}