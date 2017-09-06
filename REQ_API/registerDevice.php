<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 06/09/2017
 * Time: 12:49 PM
 */
require_once ('config.php');

if($_SERVER['REQUEST_METHOD'] == "POST"){

    $deviceID = $_POST['playerid'];
    $token = $_POST['token'];
    $matri = $_POST['matri'];

    $check = mysqli_query($con,"select * from push_service where inti = '$matri'");

    if(mysqli_num_rows($check) > 0){
        $update = mysqli_query($con,"update push_service set player = '$deviceID', token = '$token' where inti = '$matri'");

        if($update){
            echo '2';
        }else{
            echo '3';
        }

    }else{
        $query = mysqli_query($con,"insert into push_service (inti,player,token) values ('$matri','$deviceID','$token')");

        if($query){
            echo '1';
        }else{
            echo '3';
        }
    }
}else{
    echo '0';
}
?>