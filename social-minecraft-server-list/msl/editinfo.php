#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script is for editting the info of the current logged in user
*/

require 'header.php';

if( !isset($_SESSION['username'])) {
	require 'denied.php';
	die();
}

//display form with current info, then space to put new info, and an apply button

require 'dbinfo.php';

$c = connect();

$q = sprintf("select email, location, website, age, bio, minecraft_username from users where username='%s'", $_SESSION['username']);

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

echo '<br><br>';

echo 'Input a new value to edit. Leave blank otherwise';
echo '<form name="doeditinfo" action="doeditinfo.php" method="post">';
echo 'New Email: <input type="text" name="email" maxlength="50" />';
echo '<br>';
echo 'New Location: <input type="text" name="location" maxlength="30" />';
echo '<br>';
echo '<br>';
echo 'New Website: <input type="text" name="website" maxlength="80" />';
echo '<br>';
echo 'Bio: <br><textarea name="bio" rows="5" cols="35" maxlength="450" /></textarea>';
echo '<br>';
echo 'Age: <input type="text" name="age" size="3" maxlength="3" />';
echo '<br>';
echo 'Minecraft Username: <input type="text" name="minecraft_username" />';
echo '<br>';
echo '<br>';
echo 'New Password: <input type="password" name="new_password" />';
echo '<br>';
echo 'Old Password: <input type="password" name="old_password" />';
echo '<br>';
echo '<input type="submit" value="Update" />';
echo '</form>';
echo '<br>';
echo '<br>';
echo '<br>';
echo '<br>';
echo '<form name="deleteuser" action="deleteuser.php" method="post">';
echo '<input type="submit" value="Delete Account">';
echo '</form>';

disconnect($c);

require 'footer.php';

?>
