#!/usr/local/bin/php
<?php
/*
Preston Mueller / Devin Acker
3-9-11
This script has inputs for all information to be entered
when the user creates a new server. Will send the information
to make_server.php to actually make the new server

Updated 3-27-11

*/
require 'header.php';

if(!isset($_SESSION['username']) ) {
	require 'denied.php';
	die();
}

//display form with spaces for all required info for new server
?>
<form name="newserver" action="make_server.php" method="POST">
*Server name: <input type="text" name="server_title" maxlength="50" /><br>
*Description: <br>
<textarea name="description" rows="5" cols="35" maxlength="1000"></textarea><br>
*Address: <input type="text" name="server_url" maxlength="80" /><br>
Port: <input type="text" name="server_port" maxlength="5" /><br>
*Type:
<input type="radio" name="server_type" value="0" checked /> Survival
<input type="radio" name="server_type" value="1" /> Creative
<input type="radio" name="server_type" value="2" /> Other<br>

<input type="submit" value="Submit" />
</form>
<?php
require 'footer.php';

?>
