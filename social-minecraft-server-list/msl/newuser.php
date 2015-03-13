#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script validates the info from the register page
and makes a new user in the db if info is valid, otherwise
shows an error
*/
require 'header.php';

if( isset($_SESSION['username']) ) {
	exit("log out before going to register page");
}

//if is valid, add info to database, redirect to signin

require 'dbinfo.php';

$c = connect();

$valid = strcmp($_POST['password'], $_POST['verify_password'])==0 && !empty($_POST['email']) && !empty($_POST['username']);

if($valid) {
//if valid
	//username not in db already
	//password == verify_password
	//no other problems
//make new user in db

$q = sprintf("insert into users values('%s', '%s', '%s', '%s', '%s', %d, '%s', '%s')", $_POST['username'], $_POST['email'], sha1($_POST['password']), $_POST['location'], $_POST['website'], $_POST['age'], $_POST['bio'], $_POST['minecraft_username']);

//echo $q;

$cmd = ociparse($c, $q);

//$cmd = ociparse($c, "insert into users values('asdf', 'asdf@asdf.com', 'lol')");

if(!$cmd) {
  echo 'died';
  die();
}

$b = ociexecute($cmd);
//echo $b;

disconnect($c);

header('Location: signin.php');
}
else
//if not valid
   echo '<p>You were unable to be registered with the given info. Please hit back and try again with new info</p>';

require 'footer.php';

?>
