<?php
require_once('config.php');
if($_SERVER['REQUEST_METHOD'] == "POST"){
    $response = "";
    $query = "";
	$sid = $_POST['sid'];
	$service = $_POST['request'];
	if(!empty($service)){
		switch ($service){
            case "withdraw":{
                $query = "select * from withdrawReq where matri = '$sid'";
                break;
            }case "ssd":{
                $query = "select * from withdrawReq where matri = '$sid'";
                break;
            }case "transcript":{
                $query = "select * from withdrawReq where matri = '$sid'";
                break;
            }
        }
        $result = mysqli_query($con,$query);
        $row = $result->fetch_assoc();
        $response = $row['status'];
	}
	echo $response;
}
?>