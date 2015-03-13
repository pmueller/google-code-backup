#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script has an input for username and a button that
will send the username to s reset_pass.php, which will
actually reset the user's password
*/
require 'header.php';

echo '<form name="reset" action="reset_pass.php" method="post">';
echo 'Username: <input type="text" name="username" />';
echo '<br>';
echo 'A new password will be sent to your registered email';
echo '<br>';
echo '<input type="submit" value="Reset" />';
echo '</form>';

require 'footer.php';

?>
