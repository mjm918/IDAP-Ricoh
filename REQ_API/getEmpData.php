<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 06/09/2017
 * Time: 6:54 PM
 */
require_once ('config.php');
if($_SERVER['REQUEST_METHOD'] == "POST"){

    $id = $_POST['id'];
    $res = array();
    $response = "";

    $query = mysqli_query($con,"select * from einfo where matri = '$id'");

    if(mysqli_num_rows($query)>0){

        while ($row = mysqli_fetch_array($query)){

            array_push($res,array(

                "name"=>$row['name'],
                "programme"=>$row['programme'],
                "matri"=>$row['matri'],
                "working"=>$row['working'],
                "email"=>$row['email'],
                "phone"=>$row['phone']

            ));

        }
        echo json_encode(array('info' => $res));
    }else{
        $response = "2";
        echo $response;
    }
}else{
    echo '3';
}
?>