#!/usr/local/bin/php
<?php

require 'header.php';
require 'dbinfo.php';

/*
Preston Mueller / Devin Acker
3-9-11
This script will validate the info form newserver.php
and if valid, actually make the new server submission in the
db

updated 3-28-11
*/

//if no form information is set (for whatever reason), or if user isn't logged in redirect back to input form
if (!isset($_POST)) {
	header("Location: newserver.php");
}
if (!isset($_SESSION['username'])) {
	require 'denied.php';
	die();	
}

//check to make sure all required information was filled in.
// (except for port which is optional)
//later make sure the info actually makes sense (i.e. only valid characters for address/port, etc)
$required = array('server_title', 'description', 'server_url');

foreach($required as $k) {
	//check all except for port, which is optional
        if (empty($_POST[$k])) {
		//require 'denied.php';
		die('<p>One or more required fields was missing. Please try again.</p>');
	}
}

//this is a pretty sloppy way of incrementing the primary key but it's quicker
//than using a trigger to increment it, since oracle doesn't support the
//'auto increment' statement like other SQL systems. later a trigger should be
//added to the database to automatically increment server and comment IDs
$c = connect();

$cmd = ociparse($c, "select max(submission_id) as ID from server");
ociexecute($cmd);
if( ocifetch($cmd) ) {
   $key = OCIResult($cmd, "ID");
}
else die('wtfz');
$key += 1;

//if port is empty, set the default
//another hack, should be done via trigger
if( empty($_POST['server_port'] )) {
   $port = 25565;
} else $port = $_POST['server_port'];


//at some point we need to either start sanitizing the input or using prepared
//statements since this is way too insecure as it is
$stmt = sprintf("insert into server values(%d, '%s', '%s', '%s', '%s', %d, %d, %d, null)", $key, $_SESSION['username'], $_POST['server_title'], $_POST['description'], $_POST['server_url'], $port, $_POST['server_type'], 0);

$cmd = ociparse($c, $stmt);
if (!$cmd) {
	echo '<p>Error inserting to database</p>';
} else {
	ociexecute($cmd);
	echo '<p>Command was executed!</p>';
}

disconnect($c);

header("Location: serverinfo.php?id=" . $key);

require 'footer.php';

?>

