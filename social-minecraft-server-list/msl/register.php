#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script displays the fields for input for a new user
then passes the info to newuser.php to actually validate/make
the new user
*/
require 'header.php';

if( isset($_SESSION['username']) ) {
	exit("log out before going to register page");
}

echo '<form name="newuser" action="newuser.php" method="post">';
echo '*Username: <input type="text" name="username" maxlength="30" />';
echo '<br>';
echo '*Password: <input type="password" name="password" />';
echo '<br>';
echo '*Verify Password: <input type="password" name="verify_password" />';
echo '<br>';
echo '*Email: <input type="text" name="email" maxlength="50" />';
echo '<br>';
echo 'Location: <input type="text" name="location" maxlength="30" />';
echo '<br>';
echo 'Website: <input type="text" name="website" maxlength="80" />';
echo '<br>';
echo 'Age: <input type="text" name="age" size="3" maxlength="3" />';
echo '<br>';
echo 'Bio: <br><textarea name="bio" rows="5" cols="35" maxlength="450" /></textarea>';
echo '<br>';
echo 'Minecraft Username: <input type="text" name="minecraft_username" />';
echo '<br>';
echo '<input type="submit" value="Register" />';
echo '</form>';

require 'footer.php';

?>
