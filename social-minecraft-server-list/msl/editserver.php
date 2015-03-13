#!/usr/local/bin/php
<?php
/*
Preston Mueller
3-9-11
This script is for editting the info of a given server (submitted by current user)
*/
require 'header.php';

if( !isset($_SESSION['username']) ) {
	require 'denied.php';
	die();
}

//only if current user = user who submitted this server
//display form with current info, then space to put new info, and an apply button


require 'footer.php';

?>