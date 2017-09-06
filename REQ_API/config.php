<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 29/07/2017
 * Time: 1:30 AM
 */
//Defining Constants
$host = "localhost";
$user  = "id301897_fyp";
$password = "julfikar123!";
$db = "id301897_fyp";

//Connecting to Database
$con = mysqli_connect($host,$user,$password,$db) or die('Unable to Connect');
?>
