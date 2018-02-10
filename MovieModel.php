<?php

class MovieModel extends CI_Model
{
    function getAllMovies($order)
    {
        $this->db->select()->from('movie')->order_by($order, 'desc');
        $query = $this->db->get();
        return $query->result_array();
    }

//
 


 

    

    function getLogin($name, $password)         //required model
    {
        $this->db->select()->from('login')->where("name = '" . $name . "' and password = '" . $password . "'");
        $query = $this->db->get();
        return $query->first_row();
    }

    function del_search($name, $location)
    {
        $this->db->where('username', $name);
        $this->db->where('location', $location);
        $this->db->delete('search');
    }


    function del_donor($name, $no_person)
    {

        $this->db->where('name', $name);

        $this->db->where('no_person', $no_person);
       // $this->db->where('date', $date);

        $this->db->delete('donor');
    }
    //////////////////////////////////
    function del_inq($name, $location)
    {

        $this->db->where('name', $name);

        $this->db->where('location', $location);
        // $this->db->where('date', $date);

        $this->db->delete('search');
    }


    function getDonorList($location)
    {


        $today = date('Y-m-d');
        $this->db->select()->from('donor')->where("location LIKE '%" . $location . "%'and date >= '" . $today . "'");
        //$this->db->select()->from('donor')->where("location LIKE '%" . $location . "%'");
        $query = $this->db->get();
        return $query->result_array();


    }

    function getHistory($user_id)
    {


        // $today = date('Y-m-d');
        $this->db->select('name,location,contact,date')->from('history')->where("user_id = '" . $user_id . "'");
        //$this->db->select()->from('donor')->where("location LIKE '%" . $location . "%'");
        $query = $this->db->get();
        return $query->result_array();


    }


    function getInqList($location)
    {


        // $today = date('Y-m-d');
        $this->db->select()->from('search')->where("location LIKE '%" . $location . "%' ");
        $query = $this->db->get();
        return $query->result_array();


    }

} 
