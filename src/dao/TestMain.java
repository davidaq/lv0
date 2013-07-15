package dao;

import java.util.ArrayList;

import tables.Userinfo;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserinfoDao uid = new UserinfoDao();
		ArrayList<Userinfo> uList = uid.findUserinfosLikeName("02 01");
		for(Userinfo u : uList){
			System.out.println("U = " + u.getUname());
		}
	}
}
