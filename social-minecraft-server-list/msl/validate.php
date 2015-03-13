#!/usr/local/bin/php
<?php

/*
Preston Mueller
3-9-11
This script will validate a user's credentials when they attempt to sign in
*/

session_start();

//TODO: actual validation from the database

require 'dbinfo.php';

$c = connect();

$q = sprintf("select password from users where username='%s'", $_POST['username']);

$cmd = ociparse($c, $q);

if(!$cmd) {
  echo 'died';
  die();
}

ociexecute($cmd);

if(ocifetch($cmd)){
$password_hash = OCIResult($cmd, "PASSWORD");
}

disconnect($c);

if(!$password_hash) {
  echo 'Error with username or password';
  header('Location: signin.php');
}

if( strcmp($password_hash, sha1($_POST['password'])) == 0 ) {
$_SESSION['username'] = $_POST['username'];
header('Location: index.php');
}
else
   header('Location: signin.php');

?>
