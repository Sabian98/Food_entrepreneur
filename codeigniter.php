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

    public function addNewStudents()
    {
        $id = $_POST["id"];
        $name = $_POST["name"];
        $date = $_POST["date"];

        $data = array(
            'student_id' => $id,
            'student_name' => $name,
            's_date' => $date
        );
        $this->MovieModel->addNewStudents($data);
        echo true;
    }

    function addDonors()
    {

        $name = $_POST["name"];
        $contact = $_POST["contact"];
        $date = $_POST["date"];
        $location = $_POST["location"];
        $no_person = $_POST["no_person"];
        $data = array(

            'name' => $name,
            'location ' => $location,
            'contact ' => $contact,
            'date ' => $date,
            'no_person' => $no_person

        );
        $this->MovieModel->addDonors($data);
        echo true;

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

    public function setHistory()
    {
        //$password=$_POST["password"];
        $user_id = $_POST["user_id"];
        $name = $_POST["name"];
        $location = $_POST["location"];
        $contact = $_POST["contact"];
        $date = $_POST["date"];

        $data = array(
            'user_id' => $user_id,
            'name' => $name,
            'location' => $location,
            'contact' => $contact,
            'date' => $date

        );
        $this->MovieModel->addHistory($data);
        echo true;
    }


    public function check_login()  /required controller
    {
        $name = $_POST["name"];
        $password = $_POST["password"];
        $invalidLogin = ['invalid' => $username];

        $id = $this->MovieModel->getLogin($name, $password);
       // if ($login != null) {
           // echo "success";
        //} else {
           // echo "fail";
       // }


         if($id) {
            $token['id'] = $id;
            $token['username'] = $username;
            $date = new DateTime();
            $token['iat'] = $date->getTimestamp();
            $token['exp'] = $date->getTimestamp() + 60*60*5;
            
            $output['auth_token'] = JWT::encode($token, "my Secret key!");
            //$this->set_response($output, REST_Controller::HTTP_OK);
            echo json_encode($output);
        }
        else {
           //$output['auth_token']=null;
           echo "fail";
        }
    }

    public function delete_search()
    {
        $name = $_POST["name"];
        $location = $_POST["location"];

        $this->MovieModel->del_search($name, $location);
        echo true;


    }


    public function delete_donors()
    {
        $name = $_POST["name"];
        // $location = $_POST["location"];
        $no_person = $_POST["no_person"];
        // $date = $_POST["date"];

        $this->MovieModel->del_donor($name, $no_person);
        echo true;


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

    public function getHistory()

    {
        $user_id = $_POST["user_id"];
        $userid = $this->MovieModel->getHistory($user_id);//MODIFIED
        echo json_encode($userid);
    }


    public function addInqs()
    {
        $name = $_POST["name"];
        $location = $_POST["location"];
        $contact = $_POST["contact"];


        $data = array(

            'name' => $name,
            'location ' => $location,
            'contact ' => $contact


        );
        $this->MovieModel->addInqs($data);
        echo true;
    }
}
