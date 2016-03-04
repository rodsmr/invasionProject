package partita;

import java.util.ArrayList;
import java.util.Iterator;

public class Mappa {
	
		private ArrayList<Territorio> territori = null;
		Territorio t1 = null;
		Territorio t2 = null;
		Territorio t3 = null;
		Territorio t4 = null;
		Territorio t5 = null;
		Territorio t6 = null;
		Territorio t7 = null;
		Territorio t8 = null;
		Territorio t9 = null;
		Territorio t10 = null;
		Territorio t11 = null;
		Territorio t12 = null;
		Territorio t13 = null;
		Territorio t14 = null;
		Territorio t15 = null;
		Territorio t16 = null;
		Territorio t17 = null;
		Territorio t18 = null;
		Territorio t19 = null;
		Territorio t20 = null;
		Territorio t21 = null;
		Territorio t22 = null;
		Territorio t23 = null;
		Territorio t24 = null;
		Territorio t25 = null;
		Territorio t26 = null;
		Territorio t27 = null;
		Territorio t28 = null;
		Territorio t29 = null;
		Territorio t30 = null;
		Territorio t31 = null;
		Territorio t32 = null;
		Territorio t33 = null;
		Territorio t34 = null;
		Territorio t35 = null;
		Territorio t36 = null;
		Territorio t37 = null;
		Territorio t38 = null;
		Territorio t39 = null;
		Territorio t40 = null;
		Territorio t41 = null;
		Territorio t42 = null;
		
	public Mappa(){
		territori = new ArrayList<Territorio>();
		t1 = new Territorio("1","1");
		t2 = new Territorio("2","1");
		t3 = new Territorio("3","1");
		t4 = new Territorio("4","1");
		t5 = new Territorio("5","1");
		t6 = new Territorio("6","1");
		t7 = new Territorio("7","1");
		t8 = new Territorio("8","1");
		t9 = new Territorio("9","1");
		t10 = new Territorio("10","2");
		t11 = new Territorio("11","2");
		t12 = new Territorio("12","2");
		t13 = new Territorio("13","2");
		t14 = new Territorio("14","3");
		t15 = new Territorio("15","3");
		t16 = new Territorio("16","3");
		t17 = new Territorio("17","3");
		t18 = new Territorio("18","3");
		t19 = new Territorio("19","3");
		t20 = new Territorio("20","3");
		t21 = new Territorio("21","4");
		t22 = new Territorio("22","4");
		t23 = new Territorio("23","4");
		t24 = new Territorio("24","4");
		t25 = new Territorio("25","4");
		t26 = new Territorio("26","4");
		t27 = new Territorio("27","4");
		t28 = new Territorio("28","4");
		t29 = new Territorio("29","4");
		t30 = new Territorio("30","4");
		t31 = new Territorio("31","4");
		t32 = new Territorio("32","4");
		t33 = new Territorio("33","5");
		t34 = new Territorio("34","5");
		t35 = new Territorio("35","5");
		t36 = new Territorio("36","5");
		t37 = new Territorio("37","6");
		t38 = new Territorio("38","6");
		t39 = new Territorio("39","6");
		t40 = new Territorio("40","6");
		t41 = new Territorio("41","6");
		t42 = new Territorio("42","6");
	}

	public void creaMappa(){
		t1.setConfini(t2,t4,t27);
		territori.add(t1);
		t2.setConfini(t1,t3,t4,t5);
		territori.add(t2);
		t3.setConfini(t2,t5,t16,t18);
		territori.add(t3);
		t4.setConfini(t1,t2,t5,t7);
		territori.add(t4);
		t5.setConfini(t2,t3,t4,t6,t7,t8);
		territori.add(t5);
		t6.setConfini(t5,t8,t16);
		territori.add(t6);		
		t7.setConfini(t4,t5,t8,t9);
		territori.add(t7);
		t8.setConfini(t5,t6,t7,t9);
		territori.add(t8);
		t9.setConfini(t7,t8,t10);
		territori.add(t9);
		t10.setConfini(t9,t11,t12);
		territori.add(t10);
		t11.setConfini(t10,t12,t13);
		territori.add(t11);
		t12.setConfini(t10,t11,t13);
		territori.add(t12);
		t13.setConfini(t11,t12,t40);
		territori.add(t13);
		t14.setConfini(t15,t17);
		territori.add(t14);
		t15.setConfini(t14,t17,t20,t42);
		territori.add(t15);
		t16.setConfini(t6,t17);
		territori.add(t16);
		t17.setConfini(t14,t15,t16,t20);
		territori.add(t17);
		t18.setConfini(t3,t19);
		territori.add(t18);
		t19.setConfini(t18,t20);
		territori.add(t19);
		t20.setConfini(t15,t17,t19,t21,t22,t23);
		territori.add(t20);
		t21.setConfini(t20,t22,t24);
		territori.add(t21);
		t22.setConfini(t20,t21,t23,t30);
		territori.add(t22);
		t23.setConfini(t15,t20,t22,t30,t31,t42);
		territori.add(t23);
		t24.setConfini(t21,t25,t28,t29,t30);
		territori.add(t24);
		t25.setConfini(t24,t26,t28);
		territori.add(t25);
		t26.setConfini(t25,t28,t29);
		territori.add(t26);
		t27.setConfini(t1,t29);
		territori.add(t27);
		t28.setConfini(t24,t25,t26,t29);
		territori.add(t28);
		t29.setConfini(t24,t26,t27,t28,t30);
		territori.add(t29);
		t30.setConfini(t21,t22,t23,t24,t29,t31,t32);
		territori.add(t30);
		t31.setConfini(t23,t30,t32);
		territori.add(t31);
		t32.setConfini(t30,t31,t33);
		territori.add(t32);
		t33.setConfini(t32,t34);
		territori.add(t33);
		t34.setConfini(t33,t35);
		territori.add(t34);
		t35.setConfini(t34,t36);
		territori.add(t35);
		t36.setConfini(t33,t35,t37);
		territori.add(t36);
		t37.setConfini(t36,t38);
		territori.add(t37);
		t38.setConfini(t37,t39,t40);
		territori.add(t38);
		t39.setConfini(t38,t40,t41,t42);
		territori.add(t39);
		t40.setConfini(t38,t39,t41,t13);
		territori.add(t40);
		t41.setConfini(t39,t40,t42);
		territori.add(t41);
		t42.setConfini(t15,t23,t39,t41);
		territori.add(t42);
		
	}

	public ArrayList<Territorio> getLista(){
		Iterator<Territorio> it = territori.iterator();
		Territorio supporto = null;
		System.out.println("Codici dei territori: ");
		while(it.hasNext()){
			supporto = it.next();
			System.out.println(supporto.getCodice());
		}
		return this.territori;
	}
	
	
}