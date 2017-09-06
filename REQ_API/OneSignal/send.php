<?php
/**
 * Created by PhpStorm.
 * User: julfi
 * Date: 06/09/2017
 * Time: 3:35 PM
 */

function GET_APP_ID(){
    return "7d0df388-f064-48eb-9092-27fbbf0a438e";
}
function GET_BASIC_KEY(){

    $key = 'Authorization: Basic MGQ1MDhhZjMtNjE4NC00MzUzLThmMzQtYjc5YTcwZDA4MzU4';

    return $key;
}
function sendToAll($content){

    $fields = array(
        'app_id' => GET_APP_ID(),
        'included_segments' => array('Active Users'),
        'data' => array("foo" => "bar"),
        'contents' => $content
    );

    $fields = json_encode($fields);
    print("\nJSON sent:\n");
    print($fields);

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, "https://onesignal.com/api/v1/notifications");
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json; charset=utf-8', GET_BASIC_KEY()));
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
    curl_setopt($ch, CURLOPT_HEADER, FALSE);
    curl_setopt($ch, CURLOPT_POST, TRUE);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);

    $response = curl_exec($ch);
    curl_close($ch);

    return $response;
}
function sendToIndividual($playerIDs,$content){
    $fields = array(
        'app_id' => GET_APP_ID(),
        'include_player_ids' => $playerIDs,
        'data' => array("foo" => "bar"),
        'contents' => $content
    );

    $fields = json_encode($fields);
    print("\nJSON sent:\n");
    print($fields);

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, "https://onesignal.com/api/v1/notifications");
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json; charset=utf-8', GET_BASIC_KEY()));
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
    curl_setopt($ch, CURLOPT_HEADER, FALSE);
    curl_setopt($ch, CURLOPT_POST, TRUE);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);

    $response = curl_exec($ch);
    curl_close($ch);

    return $response;
}
?>