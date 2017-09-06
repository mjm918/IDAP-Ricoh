<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 04/09/2017
 * Time: 11:08 AM
 */
require_once ('config.php');
require_once ('getRole.php');
require_once ('getStudentInfo.php');

if($_SERVER['REQUEST_METHOD'] == "POST"){

    $id = $_POST['id'];

    $array = array();

    $select = mysqli_query($con,"select * from responsibility where inti = '$id'");
    $selectq = $select->fetch_assoc();
    $r = $selectq['role'];
    $programme = $selectq['programme'];

    $role = getRole($r);

    $requestW = mysqli_query($con,"select * from withdrawReq where status = '$role'");

    if(mysqli_num_rows($requestW) > 0){

        while($rowW = mysqli_fetch_array($requestW)){

            $stid = $rowW['inti'];

            $sprogramme = getStudentInfo($stid,$con);

            if($sprogramme === $programme){

                array_push($array,array(
                    "id"=>$rowW['id'],
                    "fullname"=>$rowW['fullname'],
                    "sid"=>$stid,
                    "day"=>$rowW['day'],
                    "request"=>"Withdrawal"

                ));

            }

        }

        echo json_encode(array('notification' => $array));

    }
}else{
    echo "0";
}
?>