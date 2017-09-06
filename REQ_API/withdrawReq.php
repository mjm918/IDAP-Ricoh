<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 31/07/2017
 * Time: 9:38 AM
 */
require_once ('config.php');
include ('OneSignal/send.php');
if($_SERVER['REQUEST_METHOD'] == "POST"){

    $response = "";

    $fullname = $_POST['name'];
    $ic = $_POST['ic'];
    $contact = $_POST['contact'];
    $matri = $_POST['matri'];
    $programme = $_POST['pro'];
    $email = $_POST['email'];
    $addr = $_POST['addr'];
    $bname = $_POST['bname'];
    $bacc = $_POST['bacc'];
    $bholer = $_POST['bholder'];
    $date = $_POST['date'];
    $sid = $_POST['sid'];

    $query = mysqli_query($con,"insert into withdrawReq (inti,fullname,ic,contact,matri,major,email,address,bname,bacc,bholder,date) values ('$sid','$fullname','$ic','$contact','$matri','$programme','$email','$addr','$bname','$bacc','$bholer','$date')");

    if($query){
        $response = "1";

        $message = "You have a withdrawal request from ".$fullname." (".$matri.") Please respond to make the process faster. Thank you";

        $playerIDs = array();
        $content = array(
            "en" => $message,
        );

        $selectprogramme = mysqli_query($con,"select * from sinfo where matri = '$matri'");
        $sec = $selectprogramme->fetch_assoc();
        $programme = $sec['programme'];

        $selectpro = mysqli_query($con,"select * from responsibility where programme = '$programme' and role = 'HOP'");
        while($row = mysqli_fetch_array($selectpro)){
            $rid = $row['inti'];

            $selectdevice = mysqli_query($con,"select * from push_service where inti = '$rid' order by date desc limit 1");
            $player = $selectdevice->fetch_assoc();

            $playerIDs[] = $player['player'];
        }

        sendToIndividual($playerIDs,$content);

    }else{
        $response = "2";
    }

    echo $response;

}else{
    echo '3';
}
?>