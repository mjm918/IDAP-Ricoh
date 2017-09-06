<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 29/07/2017
 * Time: 1:30 AM
 */
require_once('config.php');

if(isset($_POST['username']) && isset($_POST['password']))
{
	$result='';
	$uid = $_POST['username'];
    $password = $_POST['password'];

    $query = mysqli_query($con,"SELECT * FROM _user WHERE  uid = '$uid' AND pass = '$password'");

    if(mysqli_num_rows($query)>0){

        $get = $query->fetch_assoc();
        $isStaff = $get['isStaff'];

        if($isStaff == "0"){
            $result = "1";
        }else{
            $result = "2";
        }
    }else{
        $result = "0";
    }

    echo $result;
}
?>
