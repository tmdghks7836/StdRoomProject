package data_p.product_p.room_p;

import java.util.ArrayList;
import java.util.Calendar;

import data_p.product_p.ProductData;
import data_p.product_p.TimeData;

//룸 하나의 여러개의 시간 상품 
public class RoomProduct extends ProductData {

	public Integer personNum;
	public ArrayList<Calendar> calendarList = new ArrayList<Calendar>();;

	public RoomProduct(int id, String name, long price, int personNum) {
		super(id, name, price);
	}

	// 날짜 입력
	public void setDate(ArrayList<Calendar> calendarList) {
		for (Calendar cal : calendarList) {
			System.out.println(cal.getTime());
		}
		this.calendarList = calendarList;

	}

}
