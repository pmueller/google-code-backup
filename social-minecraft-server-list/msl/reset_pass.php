#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script is called when a user enters their name and
clicks to reset their password
*/

//start_session();

//username is in $_POST['username']
//generate random 8 char password
//set the new password in the db
//send email to the person with new password
require 'dbinfo.php';

$c = connect();

$q = sprintf("select email from users where username='%s'", $_POST['username']);

$cmd = ociparse($c, $q);

if(!$cmd) {
  echo 'died';
  die();
}

ociexecute($cmd);

if( ocifetch($cmd) ) {
  $em = OCIResult($cmd, "EMAIL");
  echo $em;
}
else {
  echo 'no such user';
  die();
} 

$pool = array_merge(range(0, 9), range('A', 'Z'));
$rand_keys = array_rand($pool, 8);
 
$password = '';
 
foreach ($rand_keys as $key) {
   $password .= $pool[$key];
}

$subject = "Minecraft Server List password reset";
$msg = "Hi. Your minecraft server list password has been reset. You new password is: " . $password . " If it was not you who reset the password, please contact an admin";

if (!mail($em, $subject, $msg)) {
  echo("<p>Message delivery failed...</p>");
   die();
  }

$q = sprintf("update users set password='%s' where username='%s'", sha1($password), $_POST['username']);
$cmd = ociparse($c, $q);
ociexecute($cmd);

disconnect($c);

header('Location: signin.php');

?>
