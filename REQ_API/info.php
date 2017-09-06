<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 29/07/2017
 * Time: 1:30 AM
 */
require_once ('config.php');
if($_SERVER['REQUEST_METHOD'] == "POST"){

    $res = array();
    $response = "";

    $sid = $_POST['sid'];

    $query = mysqli_query($con,"select * from sinfo where sid = '$sid'");

    if(mysqli_num_rows($query) > 0){
        while($row = mysqli_fetch_array($query)){

            $get = mysqli_query($con,"select * from _user where uid = '$sid'");
            $grow = $get->fetch_assoc();
            $name = $grow['name'];

            array_push($res,array(
                    "name"=>$name,
                    "matri"=>$row['matri'],
                    "major"=>$row['major'],
                    "dob"=>$row['dob'],
                    "stype"=>$row['stype'],
                    "citizen"=>$row['citizen'],
                    "ic"=>$row['ic'],
                    "email"=>$row['email'],
                    "mobile"=>$row['mobile'],
                    "bname"=>$row['bname'],
                    "bacc"=>$row['bacc'],
                    "bholder"=>$row['bholder'],
                    "street"=>$row['street'],
                    "city"=>$row['city'],
                    "state"=>$row['state'],
                    "country"=>$row['country']
                )
            );
            echo json_encode(array('info' => $res));
        }
    }else{
        $response = "2";
        echo $response;
    }
}else{
    echo '3';
}
?>