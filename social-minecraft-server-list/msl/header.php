<?php

/*
Preston Mueller
2-17-11
Header for entire site, zomg
*/


/*
Stuff this header needs to be complete:
Banner - check!
Link to Home - check!
if signed out - check!
   link to login - check!
   link to register - check!
else - check!
   username with link to edit profile - check! 
   logout - check!


TODO:
add in spacing
fix layout in css
implement user login system so that you can\
actually display the correct info

*/

session_start();

echo '<html>';
echo '<header>';
echo '<title>Social Minecraft Server List</title>';
echo '<link href="main.css" rel="stylesheet" type="text/css" />';
echo '</header>';
echo '<body>';
echo '<div id="container">';
echo '<div id ="header">';
echo '<img src="img/banner.png" />';
echo '<a href="index.php">Home</a>';

$signedin = isset($_SESSION['username']);

if($signedin) {
   $username = $_SESSION['username'];
   echo ('<a href="editinfo.php">' . $username . '</a>');
   echo '<a href="logout.php">Logout</a>';
}
else {
   echo '<a href="signin.php">Sign In</a>';
   echo '<a href="register.php">Register</a>';
}
echo '</div>';

echo '<div id="contentarea">';

?>
