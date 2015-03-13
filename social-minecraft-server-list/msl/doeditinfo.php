#!/usr/local/bin/php
<?php

/*
Preston Mueller
3-29-11
This file actually sends the updated info from
the editinfo.php page to the database
*/
session_start();

require 'dbinfo.php';

$c = connect();

if(!empty($_POST['email'])) {
  $q = sprintf("update users set email='%s' where username='%s'", $_POST['email'], $_SESSION['username']);
  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

if(!empty($_POST['location'])) {
  $q = sprintf("update users set location='%s' where username='%s'", $_POST['location'], $_SESSION['username']);

  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

if(!empty($_POST['website'])) {
  $q = sprintf("update users set website='%s' where username='%s'", $_POST['website'], $_SESSION['username']);

  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

if(!empty($_POST['bio'])) {
  $q = sprintf("update users set bio='%s' where username='%s'", $_POST['bio'], $_SESSION['username']);

  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

if(!empty($_POST['age'])) {
  $q = sprintf("update users set age='%s' where username='%s'", $_POST['age'], $_SESSION['username']);

  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

if(!empty($_POST['minecraft_username'])) {
  $q = sprintf("update users set minecraft_username='%s' where username='%s'", $_POST['minecraft_username'], $_SESSION['username']);

  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

$q = sprintf("select password from users where username='%s'", $_SESSION['username']);
$cmd = ociparse($c, $q);
ociexecute($cmd);
if (ocifetch($cmd)) {
   $password_hash = OCIResult($cmd, "PASSWORD");
}


if(!empty($_POST['new_password']) && !empty($_POST['old_password']) && strcmp(sha1($_POST['old_password']), $password_hash) == 0 ) {
  $q = sprintf("update users set password='%s' where username='%s'", sha1($_POST['new_password']), $_SESSION['username']);
  $cmd = ociparse($c, $q);
  ociexecute($cmd);
}

disconnect($c);

header("Location: editinfo.php");

?>


