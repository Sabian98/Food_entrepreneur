<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Movie extends CI_Controller
{

    function __construct()
    {
        parent::__construct();
        $this->load->helper('date');
        $this->load->library('encrypt');
        $this->load->helper("url");
    }

    public function index()
    {
        $this->load->view('movieapi_view');
    }

    public function donorDates()
    {

    }

    public function getAllStudentsForListView()
    {
        $students = $this->MovieModel->getAllStudentsForListView("student_id", "asc");//MODIFIED
        echo json_encode($students);
    }




    public function sign_up()
    {
        $password = $_POST["password"];
        $name = $_POST["name"];



        $data = array(
            // 'student_id' => $id,
            'name' => $name,
            'password' => $password
        );

        $this->MovieModel->addPerson($data);
        echo true;
    }



    public function check_login()  //required controller
    {
        $name = $_POST["name"];
        $password = $_POST["password"];
        $invalidLogin = ['invalid' => $username];
        $id = $this->MovieModel->getLogin($name, $password);
	$output = array();
         if($id) {
            $token['id'] = $id;
            $token['username'] = $username;
            $date = new DateTime();
            $token['iat'] = $date->getTimestamp();
            $token['exp'] = $date->getTimestamp() + 60*60*5;
            
            $output['auth_token'] = JWT::encode($token, "my Secret key!");
	    $output['status'] = "success";
        }
        else {
	    $output['auth_token']="null";
	    $output['status'] = "error";
        }
        echo json_encode($output);
    }

  

    public function delete_inquiry()
    {
        $name = $_POST["name"];
        $location = $_POST["location"];
        //$no_person = $_POST["no_person"];
        // $date = $_POST["date"];

        $this->MovieModel->del_inq($name, $location);
        echo true;


    }


    public function date_list()

    {
        $location = $_POST["location"];
        $donors = $this->MovieModel->getDonorList($location);//MODIFIED
        echo json_encode($donors);
    }


    public function date_list_2()

    {
        $location = $_POST["location"];
        $inqs = $this->MovieModel->getInqList($location);//MODIFIED
        echo json_encode($inqs);
    }

