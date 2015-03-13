#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script shows a username and password field
with an option to go to reset password page or sign in
*/

require 'header.php';

echo '<form name="signin" action="validate.php" method="post">';
echo 'Username: <input type="text" name="username" />';
echo '<br>';
echo 'Password: <input type="password" name="password" />';
echo '<br>';
echo '<a href="reset.php">reset password</a>';
echo '<br>';
echo '<input type="submit" value="Log in" />';
echo '</form>';

require 'footer.php';

?>
