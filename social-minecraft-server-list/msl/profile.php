#!/usr/local/bin/php
<?php

/*
Preston Mueller
3-29-11
This page should display the profile info for the person
passed in via get into variable user
*/


require 'header.php';

if( !isset($_GET['user']) ) {
   echo 'error, need to have a username';
   die();
}

require 'dbinfo.php';

$c = connect();

$q = sprintf("select email, location, website, age, bio, minecraft_username from users where username='%s'", $_GET['user']);

$cmd = ociparse($c, $q);

ociexecute($cmd);

if ( ocifetch($cmd) ) {
  $email = OCIResult($cmd, "EMAIL");
  $loc = OCIResult($cmd, "LOCATION");
  $website =  OCIResult($cmd, "WEBSITE");
  $bio =  OCIResult($cmd, "BIO");
  $age = OCIResult($cmd, "AGE");
  $minecraft_username =  OCIResult($cmd, "MINECRAFT_USERNAME");
}
else {
  echo 'error in sql';
  die();
}

echo '<h2>' . $_GET['user'] . '</h2><br>';
echo 'Email: ' . $email;
echo '<br>';
echo 'Location: ' . $loc;
echo '<br>';
echo 'Website: ' . $website;
echo '<br>';
echo 'Bio: ' . '<br>' . $bio;
echo '<br>';
echo 'Age: ' . $age;
echo '<br>';
echo 'Minecraft Username: ' . $minecraft_username;

disconnect($c);

require 'footer.php';

?>
