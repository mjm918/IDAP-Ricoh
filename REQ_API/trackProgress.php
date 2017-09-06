<?php
require_once('config.php');
if($_SERVER['REQUEST_METHOD'] == "POST"){
    $sid = $_POST['sid'];
    $res = array();
    $query = mysqli_query($con,"select * from withdrawReq where matri = '$sid'");

    if(mysqli_num_rows($query)>0){
            while($row = mysqli_fetch_array($query)){
            array_push($res,array(
                "status" => $row['status'],
                "date" => $row['day'],
                "id" => $row['id']
            )
          );
        }
        echo json_encode(array('info' => $res));
        }else{
            echo '0';
        }
}
?>