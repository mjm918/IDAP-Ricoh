<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 06/09/2017
 * Time: 3:46 PM
 */
require_once ('OneSignal/send.php');
if(isset($_POST['submit'])){
    $message = $_POST['comment'];

    $content = array(
        "en" => $message,
    );

    $response = sendToAll($content);
    $return["allresponses"] = $response;
    $return = json_encode( $return);

    print("\n\nJSON received:\n");
    print($return);
    print("\n");
}
?>
</<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet"/>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Send Notification</title>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading"><h4 style="color: #326eaf">Notification Panel</h4></div>
        <div class="panel-body">
            <h4 style="color: #326eaf">Send to Everyone:</h4>
            <form action="index.php" method="post">
                <textarea required placeholder="Write your comment here" style="resize: none;height: 150px" class="form-control" rows="5" id="comment" name="comment"></textarea>
                <input type="hidden" value="" id="id" name="id"/>
                <input type="submit" name="submit" id="submit" class="btn btn-primary" value="Send" style="margin-top: 20px"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
