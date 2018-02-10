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
    function getAllStudentsForListView($order, $orderType)
    {
        $this->db->select('student_id, student_name, s_date')->from('students')->order_by($order, $orderType);    //MODIFIED
        $query = $this->db->get();
        return $query->result_array();
    }

    function addPerson($data)

    {
        $this->db->insert('login', $data);
        return $this->db->insert_id();
    }

    function addHistory($data)

    {
        $this->db->insert('history', $data);
        return $this->db->insert_id();
    }


    function addDonors($data)

    {
        $this->db->insert('donor', $data);
        return $this->db->insert_id();
    }

    function addInqs($data)

    {
        $this->db->insert('search', $data);
        return $this->db->insert_id();
    }


    function getAllMoviesWithPagination($order, $limit, $start)
    {
        $this->db->select()->from('movie')->order_by($order, 'desc')->limit($limit, $start);
        $query = $this->db->get();
        return $query->result_array();
    }

    function getAllMoviesWithPaginationForListView($order, $limit, $start)
    {
        $this->db->select('id, title, rating, genre, release_date, list_image')->from('movie')->order_by($order, 'desc')->limit($limit, $start);
        $query = $this->db->get();
        return $query->result_array();
    }

    function updateMovie($movieId, $movie)
    {
        $this->db->where('id', $movieId);
        $this->db->update('movie', $movie);
    }

    function getMovie($movieId)
    {
        $this->db->select()->from('movie')->where(array('id' => $movieId));
        $query = $this->db->get();
        return $query->first_row('array');
    }

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