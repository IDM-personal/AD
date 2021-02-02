import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import model.Lineas;
import model.Train_Fetch_Obj;
import model.Trenes;


public class PPal {
	
	
	public static void printmenu()
	{
		System.out.println("1 - Modificar nombre cochera");
		System.out.println("2 - Mostrar línea en la que prestan servicio más trenes");
		System.out.println("3 - Ampliar línea");
		System.out.println("4 - Actualizar Estaciones");
		System.out.println("5 - Mostrar informacion de linea");
	}
	
	public static void modificarcochera()
	{
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = s.beginTransaction();
		tx.begin();
	}
	
	public static void marlinea()
	{
		int max = 0;
		int i = 0;
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createSQLQuery("SELECT cod_linea,count(trenes.cod_linea) as `val_max` from trenes"
				+ " group by trenes.cod_linea"
				+ " order by `val_max` desc");
		Iterator<Object> it = q.list().iterator();
		Train_Fetch_Obj[] lineasmax = new Train_Fetch_Obj[q.list().size()];
		while(it.hasNext())
		{
			Object[] marlinea = (Object[]) it.next();
			lineasmax[i] = new Train_Fetch_Obj(Integer.parseInt(marlinea[0].toString()),Integer.parseInt(marlinea[1].toString()));
			if(lineasmax[i].getVal_max() > max)
				max = lineasmax[i].getVal_max();
			i++;
		}
		System.out.println("Linea/s en la/s que prestan servicio mas trenes : ");
		for(Train_Fetch_Obj maxl: lineasmax)
			if(maxl.getVal_max() == max)
				System.out.println("Linea "+maxl.getCod_linea()+", Numero de trenes : "+ max);
		tx.commit();
		s.close();
	}
	
	public static void AmpliarLinea()
	{
		Session s = HibernateUtil.getSessionFactory().openSession();
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce cod linea : ");
		Transaction tx = s.beginTransaction();
		Lineas l = new Lineas();
		l.setCodLinea(4);
		l.setNombre("Central");
		s.save(l);
		int nlinea = sc.nextInt();
		System.out.println("Introduce cod estacion : ");
		int nestacion = sc.nextInt();
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int i = 0;
		printmenu();
		while((i = sc.nextInt()) != 6)
		{
			switch (i) {
			case 1:
				modificarcochera();
				break;
			case 2:
				marlinea();
				break;
			case 3:
				AmpliarLinea();
				break;
			default:
				printmenu();
				break;
			}
		}
		System.out.println("Fin del Programa");
	}

}
